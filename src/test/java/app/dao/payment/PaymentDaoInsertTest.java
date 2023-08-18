package app.dao.payment;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.entity.Payment;
import app.error.ErrorCode;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

public class PaymentDaoInsertTest {

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
  @DisplayName("결제정보 생성 테스트 - 정상 처리")
  void insert() throws Exception {
    // given
    Payment payment = Payment.builder().orderId(1L).actualAmount(10000L).build();

    // when
    int insertedRow = paymentDao.insert(payment, session);
    session.commit();
    session.close();

    // then
    assertSame(1, insertedRow);
  }

  @Test
  @DisplayName("결제정보 생성 테스트 - 존재하지 않는 주문")
  void insertEx1() throws Exception {
    // given
    Payment payment = Payment.builder().orderId(10000L).actualAmount(10000L).build();

    // when, then
    assertThrows(
        Exception.class,
        () -> paymentDao.insert(payment, session),
        ErrorCode.CANNOT_INSERT_PAYMENT.getMessage());
    session.commit();
    session.close();
  }
}
