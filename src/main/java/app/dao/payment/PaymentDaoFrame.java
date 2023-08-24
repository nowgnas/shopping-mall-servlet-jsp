package app.dao.payment;

import app.dao.cart.DaoFrame;
import app.entity.Payment;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public interface PaymentDaoFrame<K, V extends Payment> extends DaoFrame<K, V> {

  Optional<Payment> selectByOrderId(Long orderId, SqlSession session) throws Exception;
}
