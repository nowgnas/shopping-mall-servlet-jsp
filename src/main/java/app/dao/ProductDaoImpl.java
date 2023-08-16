package app.dao;

import app.entity.Product;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class ProductDaoImpl implements DaoFrame<Long, Product> {

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
    return session.selectList("product.selectall");
  }

  public List<Product> selectAllSortByPrice(SqlSession session) throws Exception {
    return session.selectList("product.sortbypricedesc");
  }
}
