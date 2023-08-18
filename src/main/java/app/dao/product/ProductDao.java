package app.dao.product;

import app.dto.product.response.ProductItemQuantity;
import app.dto.product.response.ProductListItem;
import app.dto.product.response.ProductListItemOfLike;
import app.dto.product.response.ProductQuantity;
import app.entity.Product;
import app.error.CustomException;
import app.error.ErrorCode;
import java.util.List;
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
  public List<ProductQuantity> checkProductQuantity(List<Long> productIds, SqlSession session) {
    // todo: 상품 재고 확인
    List<ProductQuantity> productQuantities =
        session.selectList("product.checkQuantity", productIds);
    session.close();
    return productQuantities;
  }

  @Override
  public List<ProductListItemOfLike> selectProductListItemOfLike(
      List<Long> productId, SqlSession session) throws CustomException {
    List<ProductListItemOfLike> productListItemOfLikes =
        session.selectList("product.selectProductListItemOfLike", productId);
    session.close();
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
    session.close();
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
    session.close();
    if (product.isPresent()) return product;
    else throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
  }

  @Override
  public List<Product> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public List<ProductListItem> selectAllSortByPriceDesc(SqlSession session) throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbypricedesc");
    session.close();
    if (products.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return products;
  }

  @Override
  public List<ProductListItem> selectAllSortByPrice(SqlSession session) throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbyprice");
    session.close();
    if (products.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return products;
  }

  @Override
  public List<ProductListItem> selectAllSortByDate(SqlSession session) throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbydate");
    session.close();
    if (products.size() == 0) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return products;
  }
}
