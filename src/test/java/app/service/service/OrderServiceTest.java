package app.service.service;

import app.dao.coupon.CouponDao;
import app.dao.delivery.DeliveryDao;
import app.dao.member.MemberDao;
import app.dao.order.OrderDao;
import app.dao.payment.PaymentDao;
import app.dao.product.ProductDao;
import app.dao.productorder.ProductOrderDao;
import app.dto.request.OrderCreateDto;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.Coupon;
import app.entity.Member;
import app.entity.Order;
import app.entity.Product;
import app.enums.CouponStatus;
import app.enums.OrderStatus;
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

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();
  private OrderServiceImpl orderService;
  private final OrderDao orderDao = new OrderDao();
  private final DeliveryDao deliveryDao = new DeliveryDao();
  private final PaymentDao paymentDao = new PaymentDao();
  private final CouponDao couponDao = new CouponDao();
  private final MemberDao memberDao = new MemberDao();
  private final ProductOrderDao productOrderDao = new ProductOrderDao();
  private final ProductDao productDao = ProductDao.getInstance();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("order/init-orderservice-data.sql", session);

    orderService = new OrderServiceImpl();
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("상품 주문 바로 구매(쿠폰 미적용) - 정상 처리")
  void createOrderWithoutCoupon() throws Exception {
    // given
    Long memberId = 1L;
    Long productId = 1L;
    Long quantity = 1L;
    OrderCreateDto.AddressDto address =
        new OrderCreateDto.AddressDto("상품 주문 테스트 도로명 주소", "상품 주문 테스트", "상품 주문 테스트");
    OrderCreateDto.ProductDto product = new OrderCreateDto.ProductDto(productId, quantity);
    Long totalPrice = 3000000L * quantity;
    OrderCreateDto orderCreateDto =
        new OrderCreateDto(memberId, null, address, product, totalPrice);

    // when
    Order order = orderService.createOrder(orderCreateDto);

    // then
    Product findProduct =
        productDao
            .selectById(orderCreateDto.getProduct().getProductId(), session)
            .orElseThrow(Exception::new);
    assertSame(1L, findProduct.getQuantity());
    Member member =
        memberDao.selectById(orderCreateDto.getMemberId(), session).orElseThrow(Exception::new);
    assertEquals(3000000L, (long) member.getMoney());
    assertNotNull(order.getId());
    assertEquals(order.getStatus(), OrderStatus.PENDING.name());
  }

  @Test
  @DisplayName("상품 주문 바로 구매 - 비정상 처리(상품 재고 부족)")
  void createOrderWithoutCouponEx1() throws Exception {
    // given
    Long memberId = 1L;
    Long productId = 1L;
    Long quantity = 3L;

    OrderCreateDto.AddressDto address =
        new OrderCreateDto.AddressDto("상품 주문 테스트 도로명 주소", "상품 주문 테스트", "상품 주문 테스트");
    OrderCreateDto.ProductDto product = new OrderCreateDto.ProductDto(productId, quantity);
    Long totalPrice = 9000000L * quantity;
    OrderCreateDto orderCreateDto =
        new OrderCreateDto(memberId, null, address, product, totalPrice);

    // when, then
    assertThrows(Exception.class, () -> orderService.createOrder(orderCreateDto), "상품의 재고가 부족합니다.");
  }

  @Test
  @DisplayName("상품 주문 바로 구매 - 비정상 처리(회원 잔고 부족)")
  void createOrderWithoutCouponEx2() throws Exception {
    // given
    Long memberId = 1L;
    Long productId = 2L;
    Long quantity = 4L;

    OrderCreateDto.AddressDto address =
            new OrderCreateDto.AddressDto("상품 주문 테스트 도로명 주소", "상품 주문 테스트", "상품 주문 테스트");
    OrderCreateDto.ProductDto product = new OrderCreateDto.ProductDto(productId, quantity);
    Long totalPrice = 2000000L * quantity;
    OrderCreateDto orderCreateDto =
            new OrderCreateDto(memberId, null, address, product, totalPrice);

    // when, then
    assertThrows(Exception.class, () -> orderService.createOrder(orderCreateDto), "회원의 잔고가 부족합니다.");
  }

  @Test
  @DisplayName("상품 주문 바로 구매(쿠폰 적용) - 정상 처리")
  void createOrderWithCoupon() throws Exception {
    // given
    Long memberId = 1L;
    Long productId = 1L;
    Long quantity = 1L;
    Long couponId = 1L;
    Long discountValue = 5000L;

    OrderCreateDto.AddressDto address =
            new OrderCreateDto.AddressDto("상품 주문 테스트 도로명 주소", "상품 주문 테스트", "상품 주문 테스트");
    OrderCreateDto.ProductDto product = new OrderCreateDto.ProductDto(productId, quantity);
    Long totalPrice = 3000000L * quantity - discountValue;
    OrderCreateDto orderCreateDto =
            new OrderCreateDto(memberId, couponId, address, product, totalPrice);

    // when
    Order order = orderService.createOrder(orderCreateDto);

    // then
    Product findProduct =
            productDao
                    .selectById(orderCreateDto.getProduct().getProductId(), session)
                    .orElseThrow(Exception::new);
    assertSame(1L, findProduct.getQuantity());
    Member member =
            memberDao.selectById(orderCreateDto.getMemberId(), session).orElseThrow(Exception::new);
    assertEquals(3005000L, (long) member.getMoney());
    Coupon coupon = couponDao.selectById(couponId, session).orElseThrow(Exception::new);
    assertEquals(coupon.getStatus(), CouponStatus.USED.name());
    assertNotNull(order.getId());
    assertEquals(order.getStatus(), OrderStatus.PENDING.name());
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
    ProductOrderDetailDto productOrderDetailDto =
        orderService.getOrderDetailsForMemberAndOrderId(orderId, memberId);
    session.commit();
    session.close();

    // then
    assertSame(productOrderDetailDto.getOrderId(), orderId);
  }
}
