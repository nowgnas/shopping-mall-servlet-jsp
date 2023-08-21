package app.dao.delivery;

import static org.junit.jupiter.api.Assertions.*;

import app.entity.Delivery;
import config.TestConfig;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

public class DeliveryDaoSelectTest {

  private DeliveryDao deliveryDao = new DeliveryDao();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("delivery/init-delivery-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("배송지 조회 테스트 - 정상 처리")
  void selectById() throws Exception {
    // given
    Long orderId = 1L;

    // when
    Optional<Delivery> optionalDelivery = deliveryDao.selectById(orderId, session);
    session.commit();
    session.close();

    // then
    Delivery findDelivery = optionalDelivery.orElse(null);
    assertNotNull(findDelivery);
    assertSame(orderId, findDelivery.getOrderId());
  }

  @Test
  @DisplayName("배송지 조회 테스트 - 존재하지 않는 주문")
  void selectByIdEx1() throws Exception {
    // given
    Long orderId = 1000L;

    // when, then
    assertThrows(
        Exception.class,
        () -> deliveryDao.selectById(orderId, session).orElseThrow(Exception::new));
    session.commit();
    session.close();
  }
}
