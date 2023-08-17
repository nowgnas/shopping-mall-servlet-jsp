package app.dao.product;

import app.entity.Product;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;

public class ProductDao implements ProductDaoFrame<Long, Product> {
  private Logger log = Logger.getLogger("order");

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
    return session.selectList("product.sortbypricedesc");
  }

  @Override
  public List<Product> selectAllSortByPrice(SqlSession session) throws Exception {
    return session.selectList("product.sortbyprice");
  }

  @Override
  public List<Product> selectAllSortByDate(SqlSession session) throws Exception {
    return session.selectList("product.sortbydate");
  }
}
