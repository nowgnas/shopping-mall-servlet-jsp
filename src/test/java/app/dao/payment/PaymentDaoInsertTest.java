package app.dao.payment;

import static org.junit.jupiter.api.Assertions.assertSame;

import app.entity.Payment;
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
}
