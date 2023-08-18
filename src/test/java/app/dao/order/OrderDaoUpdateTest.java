package app.dao.order;

import static org.junit.jupiter.api.Assertions.assertSame;

import app.entity.Order;
import app.enums.OrderStatus;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

public class OrderDaoUpdateTest {

  private final OrderDao orderDao = new OrderDao();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("order/init-order-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("주문 수정 테스트 - 정상 처리")
  void update() throws Exception {
    // given
    Order order = Order.builder().id(1L).memberId(1L).status(OrderStatus.PROCESSING.name()).build();

    // when
    int updatedRow = orderDao.update(order, session);
    session.commit();
    session.close();

    // then
    assertSame(1, updatedRow);
  }
}
