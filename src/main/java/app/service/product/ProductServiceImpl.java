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
import app.error.CustomException;
import app.error.ErrorCode;
import app.utils.GetSessionFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class ProductServiceImpl implements ProductService {
  private ProductDaoFrame dao;
  private SqlSession session;
  private static ProductServiceImpl instance;

  public static ProductServiceImpl getInstance() {
    if (instance == null) return new ProductServiceImpl();
    return instance;
  }

  public ProductServiceImpl() {
    this.dao = ProductDao.getInstance();
    this.session = GetSessionFactory.getInstance().openSession();
  }

  @Override
  public ProductDetailWithCategory getProductDetail(Long memberId, Long productId)
      throws Exception {
    Optional<ProductDetail> detail =
        Optional.ofNullable(dao.selectProductDetailWithCategory(memberId, productId, session));
    ProductDetailWithCategory productDetailWithCategory = null;
    if (detail.isPresent()) {
      ProductDetail productDetail = detail.get();
      List<Category> categories =
          dao.selectProductParentCategory(Long.valueOf(productDetail.getCategoryId()), session);
      productDetailWithCategory =
          ProductDetailWithCategory.getProductDetail(categories, productDetail);
    }
    return productDetailWithCategory;
  }

  @Override
  public ProductListWithPagination getProductList(
      Long userId, int currentPage, SortOption sortOption) throws Exception {
    Map<String, Object> map = new HashMap<>();
    map.put("current", currentPage);
    map.put("perPage", 10);
    map.put("userId", userId.toString());

    List<ProductListItem> products = null;

    switch (sortOption) {
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
        Pagination.builder().totalPage(totalPage).perPage(10).currentPage(currentPage).build();

    return ProductListWithPagination.<List<ProductListItem>, Pagination>builder()
        .item(products)
        .paging(pagination)
        .build();
  }
}
