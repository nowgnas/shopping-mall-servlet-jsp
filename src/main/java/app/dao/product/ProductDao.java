package app.dao.product;

import app.dto.product.*;
import app.dto.product.response.ProductDetailForOrder;
import app.entity.Category;
import app.entity.Product;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;

public class ProductDao implements ProductDaoFrame<Long, Product> {
  private static ProductDao instance;
  private Logger log = Logger.getLogger("order");

  private ProductDao() {}

  /**
   * 싱글톤
   *
   * @return ProductDao
   */
  public static ProductDao getInstance() {
    if (instance == null) return new ProductDao();
    return instance;
  }

  @Override
  public List<ProductListItemOfLike> selectProductListItemOfLike(
      List<Long> productId, SqlSession session) throws Exception {

    return session.selectList("product.selectProductListItemOfLike", productId);
  }

  /**
   * @param productId 상품 id list
   * @param session sql session
   * @return 상품 정보 (이름, 이미지, 가격, 개수)
   */
  @Override
  public List<ProductItemQuantity> selectProductQuantity(List<Long> productId, SqlSession session) {
    return session.selectList("product.selectone", productId);
  }

  @Override
  public int insert(Product product, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int update(Product product, SqlSession session) throws Exception {
    return session.update("product.update", product);
  }

  @Override
  public int deleteById(Long aLong, SqlSession session) throws Exception {
    return 0;
  }

  /**
   * 상품 상세 조회
   *
   * @param productId 상품 id
   * @param session sql session
   * @return 상품 객체
   * @throws Exception select error
   */
  @Override
  public Optional<Product> selectById(Long productId, SqlSession session) throws Exception {
    return Optional.ofNullable(session.selectOne("product.select", productId));
  }

  @Override
  public List<Product> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public List<ProductListItem> selectAllSortByPriceDesc(Map<String, Object> map, SqlSession session)
      throws Exception {
    return session.selectList("product.sortbypricedesc", map);
  }

  @Override
  public List<ProductListItem> selectAllSortByPrice(Map<String, Object> map, SqlSession session)
      throws Exception {
    return session.selectList("product.sortbyprice", map);
  }

  @Override
  public List<ProductListItem> selectAllSortByDate(Map<String, Object> map, SqlSession session)
      throws Exception {
    return session.selectList("product.sortbydate", map);
  }

  @Override
  public int getTotalPage(SqlSession session) {
    return session.selectOne("product.gettotalpage", 10);
  }

  @Override
  public ProductDetail selectProductDetailWithCategory(
      Long memberId, Long productId, SqlSession session) throws Exception {
    return session.selectOne(
        "product.selectDetail",
        ProductDetailParameter.builder().productId(productId).memberId(memberId).build());
  }

  /**
   * 상품 카테고리 정보
   *
   * @param categoryId 상품 정보
   * @param session
   * @return
   */
  @Override
  public List<Category> selectProductParentCategory(Long categoryId, SqlSession session) {
    return session.selectList("product.get-category", categoryId);
  }

  @Override
  public int selectProductQuantity(Long productId, SqlSession session) {
    return session.selectOne("product.check-qty", productId);
  }

  @Override
  public ProductDetailForOrder selectProductDetail(Long productId, SqlSession session)
      throws Exception {
    return session.selectOne("product.product-detail-for-order", productId);
  }

  @Override
  public List<ProductListItem> selectProductsByKeyword(
      Map<String, Object> map, SqlSession session) {
    return session.selectList("product.searchByWord", map);
  }
}
