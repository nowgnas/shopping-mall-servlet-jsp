package app.service.product;

import app.dao.product.ProductDao;
import app.dao.product.ProductDaoFrame;
import app.dto.paging.Pagination;
import app.dto.product.ProductDetail;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductDetailForOrder;
import app.dto.product.response.ProductDetailWithCategory;
import app.dto.product.response.ProductListWithPagination;
import app.entity.Category;
import app.enums.SortOption;
import app.exception.CustomException;
import app.exception.ErrorCode;
import app.utils.GetSessionFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ProductServiceImpl implements ProductService {
  private static ProductServiceImpl instance;
  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  Logger log = Logger.getLogger("ProductServiceImpl");
  private ProductDaoFrame dao;

  public ProductServiceImpl() {
    this.dao = ProductDao.getInstance();
  }

  public static ProductServiceImpl getInstance() {
    if (instance == null) return new ProductServiceImpl();
    return instance;
  }

  @Override
  public ProductDetailWithCategory getProductDetail(Long memberId, Long productId)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    Optional<ProductDetail> detail =
        Optional.ofNullable(dao.selectProductDetailWithCategory(memberId, productId, session));
    ProductDetailWithCategory productDetailWithCategory = null;
    if (detail.isPresent()) {
      ProductDetail productDetail = detail.get();
      List<Category> categories =
          dao.selectProductParentCategory(Long.valueOf(productDetail.getCategoryId()), session);
      productDetailWithCategory =
          ProductDetailWithCategory.getProductDetail(categories, productDetail);
    } else throw new Exception("상품이 없습니다");
    session.close();
    return productDetailWithCategory;
  }

  @Override
  public ProductListWithPagination getProductList(Long userId, int currentPage, String sortOption)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    Map<String, Object> map = new HashMap<>();
    map.put("current", currentPage);
    map.put("perPage", 9);
    map.put("userId", userId.toString());

    List<ProductListItem> products = null;

    switch (SortOption.valueOf(sortOption)) {
      case PRICE_DESC:
        products = dao.selectAllSortByPriceDesc(map, session);
        break;
      case PRICE_ASC:
        products = dao.selectAllSortByPrice(map, session);
        break;
      case DATE_DESC:
        products = dao.selectAllSortByDate(map, session);
        break;
      default:
        throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    }
    int totalPage = dao.getTotalPage(session);
    session.close();
    Pagination pagination =
        Pagination.builder().totalPage(totalPage).perPage(9).currentPage(currentPage).build();

    return ProductListWithPagination.<List<ProductListItem>, Pagination>builder()
        .item(products)
        .paging(pagination)
        .build();
  }

  @Override
  public ProductDetailForOrder getProductDetailForOrder(Long productId, int quantity)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    int qty = dao.selectProductQuantity(productId, session);
    if (qty < quantity) throw new Exception("주문 가능한 수량이 부족합니다");
    ProductDetailForOrder detail = dao.selectProductDetail(productId, session);
    session.close();
    return detail;
  }

  @Override
  public ProductListWithPagination getProductsByKeyword(String keyword, Long memberId, int curPage)
      throws Exception {
    log.info(
        "request info : keyword => "
            + keyword
            + " member id => "
            + memberId
            + " cur page => "
            + curPage);
    SqlSession session = sessionFactory.openSession();
    Map<String, Object> map = new HashMap<>();
    map.put("userId", memberId);
    map.put("current", curPage);
    map.put("keyword", keyword.trim());
    List<ProductListItem> products = dao.selectProductsByKeyword(map, session);
    log.info(products.toString());
    log.info("product item size " + products.size());
    session.close();
    int totalPage = (int) Math.ceil(products.size() / 9);
    Pagination pagination = Pagination.builder().currentPage(curPage).perPage(9).build();
    return ProductListWithPagination.makeListWithPaging(products, pagination, totalPage);
  }
}
