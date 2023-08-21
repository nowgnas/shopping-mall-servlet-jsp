package app.dao.delivery;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.entity.Delivery;
import app.error.ErrorCode;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

public class DeliveryDaoInsertTest {

  private DeliveryDao deliveryDao = new DeliveryDao();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("delivery/init-delivery-insert-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("배송지 생성 테스트 - 정상 처리")
  void insert() throws Exception {
    // given
    Delivery delivery =
        Delivery.builder()
            .orderId(1L)
            .roadName("도로명 주소")
            .addrDetail("상세 주소")
            .zipCode("12345")
            .build();

    // when
    int insertedRow = deliveryDao.insert(delivery, session);
    session.commit();
    session.close();

    // then
    assertSame(1, insertedRow);
  }

  @Test
  @DisplayName("배송지 생성 테스트 - 존재하지 않는 주문")
  void insertEx1() throws Exception {
    // given
    Delivery delivery =
        Delivery.builder()
            .orderId(10000L)
            .roadName("도로명 주소")
            .addrDetail("상세 주소")
            .zipCode("12345")
            .build();

    // when, then
    assertThrows(
        Exception.class,
        () -> deliveryDao.insert(delivery, session),
        ErrorCode.CANNOT_INSERT_DELIVERY.getMessage());
    session.commit();
    session.close();
  }
}
