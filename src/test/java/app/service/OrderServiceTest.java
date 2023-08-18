package app.service;

import app.dao.delivery.DeliveryDao;
import app.dao.order.OrderDao;
import app.dao.payment.PaymentDao;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.service.order.OrderServiceImpl;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderServiceTest {

  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();
  private final OrderDao orderDao = new OrderDao();
  private final DeliveryDao deliveryDao = new DeliveryDao();
  private final PaymentDao paymentDao = new PaymentDao();
  private OrderServiceImpl orderService;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("order/init-order-data.sql", session);

    orderService = new OrderServiceImpl(orderDao, deliveryDao, paymentDao);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("회원 id로 주문 모두 조회 테스트 - 정상 처리")
  void getProductOrdersForMemberCurrentYear() throws Exception {
    // given
    Long memberId = 1L;

    // when
    List<ProductOrderDto> productOrderDtos =
        orderService.getProductOrdersForMemberCurrentYear(memberId);
    session.commit();
    session.close();

    // then
    assertSame(productOrderDtos.size(), 1);
    assertSame(productOrderDtos.get(0).getProducts().size(), 5);
  }

  @Test
  @DisplayName("주문 id, 회원 id로 주문 조회 테스트 - 정상 처리")
  void getOrderDetailsForMemberAndOrderId() throws Exception {
    // given
    Long orderId = 1L;
    Long memberId = 1L;

    // when
    ProductOrderDetailDto productOrderDetailDto = orderService.getOrderDetailsForMemberAndOrderId(orderId, memberId);
    session.commit();
    session.close();

    // then
    assertSame(productOrderDetailDto.getOrderId(), orderId);
  }
}
