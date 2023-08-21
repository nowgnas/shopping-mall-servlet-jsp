package app.dao.product;

import app.dto.product.ProductDetail;
import app.dto.product.ProductDetailParameter;
import app.dto.product.ProductItemQuantity;
import app.dto.product.ProductListItem;
import app.dto.product.ProductListItemOfLike;
import app.dto.product.response.ProductDetailForOrder;
import app.entity.Category;
import app.entity.Product;
import app.error.CustomException;
import app.error.ErrorCode;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;

public class ProductDao implements ProductDaoFrame<Long, Product> {
  private Logger log = Logger.getLogger("order");
  private static ProductDao instance;

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
      List<Long> productId, SqlSession session) throws CustomException {
    List<ProductListItemOfLike> productListItemOfLikes =
        session.selectList("product.selectProductListItemOfLike", productId);
    if (productListItemOfLikes.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return productListItemOfLikes;
  }

  /**
   * @param productId 상품 id list
   * @param session sql session
   * @return 상품 정보 (이름, 이미지, 가격, 개수)
   */
  @Override
  public List<ProductItemQuantity> selectProductQuantity(List<Long> productId, SqlSession session) {
    List<ProductItemQuantity> product = session.selectList("product.selectone", productId);
    if (product.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return product;
  }

  @Override
  public int insert(Product product, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int update(Product product, SqlSession session) throws Exception {
    return 0;
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
    Optional<Product> product = session.selectOne("product.select", productId);
    if (product.isPresent()) return product;
    else throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
  }

  @Override
  public List<Product> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public List<ProductListItem> selectAllSortByPriceDesc(Map<String, Object> map, SqlSession session)
      throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbypricedesc", map);
    if (products.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return products;
  }

  @Override
  public List<ProductListItem> selectAllSortByPrice(Map<String, Object> map, SqlSession session)
      throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbyprice");
    if (products.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return products;
  }

  @Override
  public List<ProductListItem> selectAllSortByDate(Map<String, Object> map, SqlSession session)
      throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbydate");
    if (products.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return products;
  }

  @Override
  public int getTotalPage(SqlSession session) {
    return session.selectOne("product.gettotalpage", 10);
  }

  @Override
  public ProductDetail selectProductDetailWithCategory(
      Long memberId, Long productId, SqlSession session) {
    return session.selectOne(
        "product.select",
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
}
