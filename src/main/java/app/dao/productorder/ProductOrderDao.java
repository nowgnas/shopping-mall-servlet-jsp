package app.dao.productorder;

import app.entity.ProductOrder;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

public class ProductOrderDao implements ProductOrderDaoFrame<Long, ProductOrder> {

  private Logger log = Logger.getLogger("productOrder");

  @Override
  public int insert(ProductOrder productOrder, SqlSession session) throws Exception {
    return session.insert("productOrder.insert", productOrder);
  }

  @Override
  public int update(ProductOrder productOrder, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(Long aLong, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<ProductOrder> selectById(Long aLong, SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<ProductOrder> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public List<ProductOrder> selectAllByOrderId(Long orderId, SqlSession session) {
    return session.selectList("productOrder.selectAllByOrderId", orderId);
  }
}
