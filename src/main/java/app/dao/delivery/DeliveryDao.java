package app.dao.delivery;

import app.entity.Delivery;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class DeliveryDao implements DeliveryDaoFrame<Long, Delivery> {

  @Override
  public int insert(Delivery delivery, SqlSession session) throws Exception {
    return session.insert("delivery.insert", delivery);
  }

  @Override
  public int update(Delivery delivery, SqlSession session) throws Exception {
    return session.update("delivery.update", delivery);
  }

  @Override
  public int deleteById(Long orderId, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<Delivery> selectById(Long orderId, SqlSession session) throws Exception {
    return Optional.ofNullable(session.selectOne("delivery.select", orderId));
  }

  @Override
  public List<Delivery> selectAll(SqlSession session) throws Exception {
    return null;
  }
}
