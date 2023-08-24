package app.dao.product;

import app.entity.ProductImage;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class ProductImageDao implements ProductImageFrame<Long, ProductImage> {

  private static ProductImageDao instance;
  Logger log = Logger.getLogger("ProductImageDao");

  public static ProductImageDao getInstance() {
    if (instance == null) {
      return new ProductImageDao();
    }
    return instance;
  }

  @Override
  public int insert(ProductImage productImage, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int update(ProductImage productImage, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(Long productId, SqlSession session) throws Exception {
    return 0;
  }

  /**
   * 상품 이미지 조회
   *
   * @param productId 상품 아이디
   * @param session   sql session
   * @return ProductImage
   * @throws Exception 조회 오류
   */
  @Override
  public Optional<ProductImage> selectById(Long productId, SqlSession session) throws Exception {
    log.info("product id : " + productId);
    ProductImage productImage = session.selectOne("productImage.select", productId);
    session.close();
    return Optional.ofNullable(productImage);
  }

  @Override
  public List<ProductImage> selectAll(SqlSession session) throws Exception {
    return null;
  }
}
