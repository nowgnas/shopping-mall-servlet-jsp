package app.service.product;

import app.dao.product.ProductDao;
import app.dao.product.ProductDaoFrame;
import app.dto.paging.Pagination;
import app.dto.product.ProductDetail;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductDetailWithCategory;
import app.dto.product.response.ProductListWithPagination;
import app.entity.Category;
import app.enums.SortOption;
import app.exception.product.ProductNotFoundException;
import app.utils.GetSessionFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class ProductServiceImpl implements ProductService {
  private static ProductServiceImpl instance;
  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private ProductDaoFrame<Long, app.entity.Product> dao;

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

    ProductDetail productDetail =
        Optional.ofNullable(dao.selectProductDetailWithCategory(memberId, productId, session))
            .orElseThrow(ProductNotFoundException::new);

    List<Category> categories =
        dao.selectProductParentCategory(Long.valueOf(productDetail.getCategoryId()), session);
    ProductDetailWithCategory productDetail1 =
        ProductDetailWithCategory.getProductDetail(categories, productDetail);

    session.close();
    return productDetail1;
  }

  @Override
  public ProductListWithPagination getProductList(Long userId, int currentPage, String sortOption)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    Map<String, Object> map = new HashMap<>();
    map.put("offset", (currentPage - 1) * 9);
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
        throw new ProductNotFoundException();
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
  public ProductListWithPagination getProductsByKeyword(String keyword, Long memberId, int curPage)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    Map<String, Object> map = new HashMap<>();
    map.put("userId", memberId);
    map.put("offset", (curPage - 1) * 9);
    map.put("keyword", keyword.trim());
    List<ProductListItem> products = dao.selectProductsByKeyword(map, session);
    session.close();
    int totalPage = (int) Math.ceil(products.size() / 9);
    Pagination pagination = Pagination.builder().currentPage(curPage).perPage(9).build();
    return ProductListWithPagination.makeListWithPaging(products, pagination, totalPage);
  }
}
