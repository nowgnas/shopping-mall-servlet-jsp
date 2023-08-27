package app.service.order.manager;

import app.dao.payment.PaymentDao;
import app.entity.Payment;
import app.exception.payment.PaymentEntityNotFoundException;
import org.apache.ibatis.session.SqlSession;

public class OrderPaymentManager {

  private final PaymentDao paymentDao = new PaymentDao();

  public Payment determinePaymentByOrderId(Long orderId, SqlSession session) throws Exception {
    return paymentDao
        .selectByOrderId(orderId, session)
        .orElseThrow(PaymentEntityNotFoundException::new);
  }

  public int createPayment(Payment payment, SqlSession session) throws Exception {
    return paymentDao.insert(payment, session);
  }
}
