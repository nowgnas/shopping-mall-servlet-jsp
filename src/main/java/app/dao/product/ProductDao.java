package app.dao.product;

import app.dto.product.ProductItemQuantity;
import app.entity.Product;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;

public class ProductDao implements ProductDaoFrame<Long, Product> {
  private Logger log = Logger.getLogger("order");
  private static ProductDao instance;

  private ProductDao() {}

  public static ProductDao getInstance() {
    if (instance == null) return new ProductDao();
    return instance;
  }

  @Override
  public ProductItemQuantity selectProductQuantity(Long productId, SqlSession session) {
    ProductItemQuantity product = session.selectOne("product.selectone", productId);
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

  @Override
  public Optional<Product> selectById(Long aLong, SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<Product> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public List<Product> selectAllSortByPriceDesc(SqlSession session) throws Exception {
    List<Product> products = null;
    try {
      products = session.selectList("product.sortbypricedesc");
    } finally {
      session.close();
    }
    return products;
  }

  @Override
  public List<Product> selectAllSortByPrice(SqlSession session) throws Exception {
    List<Product> products = null;
    try {
      products = session.selectList("product.sortbyprice");
    } finally {
      session.close();
    }
    return products;
  }

  @Override
  public List<Product> selectAllSortByDate(SqlSession session) throws Exception {
    List<Product> products = null;
    try {
      products = session.selectList("product.sortbydate");
    } finally {
      session.close();
    }
    return products;
  }

}
