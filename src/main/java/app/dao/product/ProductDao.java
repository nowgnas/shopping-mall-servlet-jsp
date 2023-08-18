package app.dao.product;

import app.dto.product.ProductItemQuantity;
import app.dto.product.ProductListItem;
import app.entity.Product;
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

  /**
   * @param productId 상품 id list
   * @param session sql session
   * @return 상품 정보 (이름, 이미지, 가격, 개수)
   */
  @Override
  public List<ProductItemQuantity> selectProductQuantity(List<Long> productId, SqlSession session) {
    List<ProductItemQuantity> product = session.selectList("product.selectone", productId);
    session.close();
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
    return product;
  }

  @Override
  public List<Product> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public List<ProductListItem> selectAllSortByPriceDesc(SqlSession session) throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbypricedesc");
    session.close();
    return products;
  }

  @Override
  public List<ProductListItem> selectAllSortByPrice(SqlSession session) throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbyprice");
    session.close();
    return products;
  }

  @Override
  public List<ProductListItem> selectAllSortByDate(SqlSession session) throws Exception {
    List<ProductListItem> products = session.selectList("product.sortbydate");
    session.close();
    return products;
  }
}
