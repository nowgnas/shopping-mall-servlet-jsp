package app.dao.payment;

import app.entity.Payment;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

public class PaymentDao implements PaymentDaoFrame<Long, Payment> {

  @Override
  public int insert(Payment payment, SqlSession session) throws Exception {
    return session.insert("payment.insert", payment);
  }

  @Override
  public int update(Payment payment, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(Long aLong, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<Payment> selectById(Long aLong, SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<Payment> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public Optional<Payment> selectByOrderId(Long orderId, SqlSession session) throws Exception {
    return Optional.ofNullable(session.selectOne("payment.selectByOrderId", orderId));
  }
}
