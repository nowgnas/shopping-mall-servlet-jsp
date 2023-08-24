package app.dao.payment;

import app.dao.DaoFrame;
import app.entity.Payment;
import org.apache.ibatis.session.SqlSession;

import java.util.Optional;

public interface PaymentDaoFrame<K, V extends Payment> extends DaoFrame<K, V> {

  Optional<Payment> selectByOrderId(Long orderId, SqlSession session) throws Exception;
}
