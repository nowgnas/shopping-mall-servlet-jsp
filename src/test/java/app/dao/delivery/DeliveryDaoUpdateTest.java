package app.dao.delivery;

import app.entity.Delivery;
import app.enums.DeliveryStatus;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryDaoUpdateTest {

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
  @DisplayName("배송 상태 수정 테스트 - 배송 취소")
  void update() throws Exception {
    // given
    Long orderId = 1L;
    Delivery updatedDelivery =
        Delivery.builder()
            .orderId(orderId)
            .roadName("도로명 주소")
            .addrDetail("상세 주소")
            .zipCode("12345")
            .status(DeliveryStatus.CANCELED.name())
            .build();

    // when
    int updatedRow = deliveryDao.update(updatedDelivery, session);
    session.commit();

    // then
    Delivery findDelivery = deliveryDao.selectById(orderId, session).get();
    assertSame(1, updatedRow);
    assertEquals(DeliveryStatus.CANCELED.name(), findDelivery.getStatus());
    assertTrue(findDelivery.getUpdatedAt().isAfter(findDelivery.getCreatedAt()));

    session.close();
  }
}
