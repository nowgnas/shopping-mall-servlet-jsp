package app.service.order;

import static org.junit.jupiter.api.Assertions.*;

import app.dao.CartDaoFrameImpl;
import app.dao.coupon.CouponDao;
import app.dao.delivery.DeliveryDao;
import app.dao.member.MemberDao;
import app.dao.order.OrderDao;
import app.dao.product.ProductDao;
import app.dao.productorder.ProductOrderDao;
import app.dto.order.request.OrderCartCreateDto;
import app.dto.order.request.OrderCreateDto;
import app.dto.order.response.ProductOrderDetailDto;
import app.dto.order.response.ProductOrderDto;
import app.entity.*;
import app.enums.CouponStatus;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import config.TestConfig;
import java.util.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

public class OrderServiceTest {

  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();
  private OrderService orderService;
  private final OrderDao orderDao = new OrderDao();
  private final DeliveryDao deliveryDao = new DeliveryDao();
  private final CouponDao couponDao = new CouponDao();
  private final MemberDao memberDao = new MemberDao();
  private final ProductOrderDao productOrderDao = new ProductOrderDao();
  private final ProductDao productDao = ProductDao.getInstance();
  private final CartDaoFrameImpl carDao = new CartDaoFrameImpl();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("order/init-orderservice-data.sql", session);

    orderService = new OrderService();
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
    OrderCreateDto orderCreateDto =
        OrderCreateDto.builder()
            .memberId(1L)
            .roadName("상품 주문 테스트 도로명 주소")
            .addrDetail("상품 주문 테스트")
            .zipCode("상품 주문 테스트")
            .productId(1L)
            .price(3000000L)
            .quantity(1L)
            .couponId(null)
            .totalPrice(3000000L)
            .build();

    // when
    Order order = orderService.createOrder(orderCreateDto);

    /* then 1. 상품의 재고가 주문한 개수만큼 감소 2. 회원의 잔고가 총 주문 금액만큼 감소 4. 주문 생성 */
    Product findProduct =
        productDao.selectById(orderCreateDto.getProductId(), session).orElseThrow(Exception::new);
    assertSame(1L, findProduct.getQuantity());
    Member member =
        memberDao.selectById(orderCreateDto.getMemberId(), session).orElseThrow(Exception::new);
    assertEquals(3000000L, (long) member.getMoney());
    assertNotNull(order.getId());
    assertEquals(OrderStatus.PENDING.name(), order.getStatus());
  }

  @Test
  @DisplayName("상품 주문 바로 구매 - 비정상 처리(상품 재고 부족)")
  void createOrderWithoutCouponEx1() throws Exception {
    // given
    OrderCreateDto orderCreateDto =
        OrderCreateDto.builder()
            .memberId(1L)
            .roadName("상품 주문 테스트 도로명 주소")
            .addrDetail("상품 주문 테스트")
            .zipCode("상품 주문 테스트")
            .productId(1L)
            .price(3000000L)
            .quantity(3L)
            .couponId(null)
            .totalPrice(9000000L)
            .build();

    /* when, then 1. 상품의 재고가 감소되지 않아야함 2. 회원의 잔고가 감소되지 않아야함 4. 주문 생성 처리 X */
    assertThrows(
        Exception.class,
        () -> {
          Order order = orderService.createOrder(orderCreateDto);
          assertNull(order.getId());
        },
        "상품의 재고가 부족합니다.");
    Product findProduct =
        productDao.selectById(orderCreateDto.getProductId(), session).orElseThrow(Exception::new);
    assertSame(2L, findProduct.getQuantity());
    Member member =
        memberDao.selectById(orderCreateDto.getMemberId(), session).orElseThrow(Exception::new);
    assertEquals(6000000L, (long) member.getMoney());
  }

  @Test
  @DisplayName("상품 주문 바로 구매 - 비정상 처리(회원 잔고 부족)")
  void createOrderWithoutCouponEx2() throws Exception {
    // given
    OrderCreateDto orderCreateDto =
        OrderCreateDto.builder()
            .memberId(1L)
            .roadName("상품 주문 테스트 도로명 주소")
            .addrDetail("상품 주문 테스트")
            .zipCode("상품 주문 테스트")
            .productId(2L)
            .price(2000000L)
            .quantity(4L)
            .couponId(null)
            .totalPrice(8000000L)
            .build();

    /* when, then 1. 상품의 재고가 감소되지 않아야함 2. 회원의 잔고가 감소되지 않아야함 4. 주문 생성 처리 X */
    assertThrows(
        Exception.class,
        () -> {
          Order order = orderService.createOrder(orderCreateDto);
          assertNull(order.getId());
        },
        "회원의 잔고가 부족합니다.");
    Product findProduct =
        productDao.selectById(orderCreateDto.getProductId(), session).orElseThrow(Exception::new);
    assertSame(10L, findProduct.getQuantity());
    Member member =
        memberDao.selectById(orderCreateDto.getMemberId(), session).orElseThrow(Exception::new);
    assertEquals(6000000L, (long) member.getMoney());
  }

  @Test
  @DisplayName("상품 주문 바로 구매(쿠폰 적용) - 정상 처리")
  void createOrderWithCoupon() throws Exception {
    // given
    OrderCreateDto orderCreateDto =
        OrderCreateDto.builder()
            .memberId(1L)
            .roadName("상품 주문 테스트 도로명 주소")
            .addrDetail("상품 주문 테스트")
            .zipCode("상품 주문 테스트")
            .productId(1L)
            .price(2000000L)
            .quantity(1L)
            .couponId(1L)
            .totalPrice(2995000L)
            .build();

    // when
    Order order = orderService.createOrder(orderCreateDto);

    /* then 1. 상품의 재고가 주문한 개수만큼 감소 2. 회원의 잔고가 총 주문 금액만큼 감소 3. 쿠폰 상태 '사용됨'으로 처리 4. 주문 생성 */
    Product findProduct =
        productDao.selectById(orderCreateDto.getProductId(), session).orElseThrow(Exception::new);
    assertSame(1L, findProduct.getQuantity());
    Member member =
        memberDao.selectById(orderCreateDto.getMemberId(), session).orElseThrow(Exception::new);
    assertEquals(3005000L, (long) member.getMoney());
    Coupon coupon = couponDao.selectById(1L, session).orElseThrow(Exception::new);
    assertEquals(CouponStatus.USED.name(), coupon.getStatus());
    assertNotNull(order.getId());
    assertEquals(OrderStatus.PENDING.name(), order.getStatus());
  }

  @Test
  @DisplayName("장바구니 상품 주문(쿠폰 미적용) - 정상 처리")
  void createCartOrderWithoutCoupon() throws Exception {
    // given
    Long memberId = 1L;

    List<OrderCartCreateDto.ProductDto> products = new ArrayList<>();
    products.add(new OrderCartCreateDto.ProductDto(6L, 1L, 5L));
    products.add(new OrderCartCreateDto.ProductDto(7L, 2L, 5L));
    products.add(new OrderCartCreateDto.ProductDto(8L, 1L, 5L));
    OrderCartCreateDto orderCartCreateDto = OrderCartCreateDto.builder()
            .memberId(1L)
            .couponId(null)
            .roadName("상품 주문 테스트 도로명 주소")
            .addrDetail("상품 주문 테스트")
            .zipCode("상품 주문 테스트")
            .products(products)
            .totalPrice(1000000L * 1L + 600000L * 2L + 500000L * 1L)
            .build();

    // when
    Order order = orderService.createCartOrder(orderCartCreateDto);

    /* then 1. 상품들의 재고가 주문한 개수만큼 감소 2. 회원의 잔고가 총 주문 금액만큼 감소 3. 주문 생성 4. 주문한 상품들이 장바구니에 있으면 제거 */
    Product findProduct1 = productDao.selectById(6L, session).orElseThrow(Exception::new);
    assertSame(95L, findProduct1.getQuantity());
    Product findProduct2 = productDao.selectById(7L, session).orElseThrow(Exception::new);
    assertSame(75L, findProduct2.getQuantity());
    Product findProduct3 = productDao.selectById(8L, session).orElseThrow(Exception::new);
    assertSame(85L, findProduct3.getQuantity());

    List<Cart> carts = carDao.getCartProductListByMember(memberId, session);
    assertSame(0, carts.size());

    Member member =
        memberDao.selectById(orderCartCreateDto.getMemberId(), session).orElseThrow(Exception::new);
    assertEquals(3300000L, (long) member.getMoney());
    assertNotNull(order.getId());
    assertEquals(order.getStatus(), OrderStatus.PENDING.name());

    List<ProductOrder> productOrders = productOrderDao.selectAllByOrderId(order.getId(), session);
    assertSame(3, productOrders.size());
  }

  @Test
  @DisplayName("상품 주문 취소 - 정상 처리")
  void cancelOrderWithoutCoupon() throws Exception {
    // given
    Long orderId = 1L;
    Long memberId = 1L;
    Long couponId = 1L;

    // when
    orderService.cancelOrder(orderId);

    // then
    Order order = orderDao.selectById(orderId, session).orElseThrow(Exception::new);
    assertEquals(OrderStatus.CANCELED.name(), order.getStatus());
    Delivery delivery = deliveryDao.selectById(orderId, session).orElseThrow(Exception::new);
    assertEquals(DeliveryStatus.CANCELED.name(), delivery.getStatus());
    Member member = memberDao.selectById(memberId, session).orElseThrow(Exception::new);
    assertEquals(6000000L + 28500000L, member.getMoney());
    Coupon coupon = couponDao.selectById(couponId, session).orElseThrow(Exception::new);
    assertEquals(CouponStatus.UNUSED.name(), coupon.getStatus());
    productOrderDao
        .selectAllByOrderId(orderId, session)
        .forEach(
            po -> {
              try {
                Product product =
                    productDao.selectById(po.getProductId(), session).orElseThrow(Exception::new);
                Long productId = product.getId();
                if (productId == 1L) {
                  assertEquals(3, product.getQuantity());
                } else if (productId == 2L) {
                  assertEquals(12, product.getQuantity());
                } else if (productId == 3L) {
                  assertEquals(8, product.getQuantity());
                } else if (productId == 4L) {
                  assertEquals(12, product.getQuantity());
                } else if (productId == 5L) {
                  assertEquals(60, product.getQuantity());
                }
              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
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
