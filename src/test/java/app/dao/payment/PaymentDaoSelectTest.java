package app.dao.payment;

import static org.junit.jupiter.api.Assertions.*;

import app.dao.delivery.DeliveryDao;
import app.entity.Delivery;
import app.entity.Payment;
import config.TestConfig;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

public class PaymentDaoSelectTest {

  private PaymentDao paymentDao = new PaymentDao();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("payment/init-payment-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("결제정보 조회 테스트 - 정상 처리")
  void selectByOrderId() throws Exception {
    // given
    Long orderId = 1L;

    // when
    Optional<Payment> optionalPayment = paymentDao.selectByOrderId(orderId, session);
    session.commit();
    session.close();

    // then
    Payment findPayment = optionalPayment.orElse(null);
    assertNotNull(findPayment);
    assertSame(orderId, findPayment.getOrderId());
  }
}
