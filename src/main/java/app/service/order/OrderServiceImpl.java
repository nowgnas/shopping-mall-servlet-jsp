package app.service.order;

import app.dao.CartDaoFrameImpl;
import app.dao.coupon.CouponDao;
import app.dao.delivery.DeliveryDao;
import app.dao.exception.CustomException;
import app.dao.member.MemberDao;
import app.dao.order.OrderDao;
import app.dao.payment.PaymentDao;
import app.dao.product.ProductDao;
import app.dao.productorder.ProductOrderDao;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.dto.form.OrderCreateForm;
import app.dto.product.response.ProductDetailForOrder;
import app.dto.request.CartOrderCreateDto;
import app.dto.request.OrderCreateDto;
import app.dto.response.OrderMemberDetail;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.*;
import app.enums.CouponStatus;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import app.enums.PaymentType;
import app.utils.GetSessionFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;

public class OrderServiceImpl {

  private Logger log = Logger.getLogger("order");
  private final ModelMapper mapper = new ModelMapper();
  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private final OrderDao orderDao = new OrderDao();
  private final DeliveryDao deliveryDao = new DeliveryDao();
  private final PaymentDao paymentDao = new PaymentDao();
  private final CouponDao couponDao = new CouponDao();
  private final MemberDao memberDao = new MemberDao();
  private final ProductOrderDao productOrderDao = new ProductOrderDao();
  private final ProductDao productDao = ProductDao.getInstance();
  private final CartDaoFrameImpl cartDao = new CartDaoFrameImpl();

  // TODO: 상품 주문 폼
  public OrderCreateForm getCreateOrderForm(Long memberId, Long productId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      ProductDetailForOrder productDetail = productDao.selectProductDetail(productId, session);
      OrderMemberDetail orderMemberDetail =
          memberDao.selectAddressAndCouponById(memberId, session).orElseThrow(Exception::new);
      return new OrderCreateForm(
          orderMemberDetail.getName(),
          new OrderCreateForm.ProductDto(
              productDetail.getId(),
              productDetail.getName(),
              productDetail.getUrl(),
              productDetail.getPrice()),
          new OrderCreateForm.AddressDto(
              orderMemberDetail.getAddress().getRoadName(),
              orderMemberDetail.getAddress().getAddrDetail(),
              orderMemberDetail.getAddress().getZipCode()),
          orderMemberDetail.getCoupons().stream()
              .map(
                  c ->
                      new OrderCreateForm.CouponDto(
                          c.getId(),
                          c.getName(),
                          c.getDiscountPolicy(),
                          c.getDiscountValue(),
                          c.getStatus()))
              .collect(Collectors.toList()));
    } catch (CustomException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  // TODO: 상품 주문
  public Order createOrder(OrderCreateDto orderCreateDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      Long memberId = orderCreateDto.getMemberId();
      /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
      Product product =
          productDao
              .selectById(orderCreateDto.getProduct().getProductId(), session)
              .orElseThrow(Exception::new);
      validateEnoughStockQuantity(product.getQuantity(), orderCreateDto.getProduct().getQuantity());
      product.updateQuantity(product.getQuantity() - orderCreateDto.getProduct().getQuantity());
      if (productDao.update(product, session) == 0) {
        throw new CustomException("상품 수량 업데이트 오류");
      }

      /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
      Member member =
          memberDao.selectById(orderCreateDto.getMemberId(), session).orElseThrow(Exception::new);
      validateEnoughMoney(member.getMoney(), orderCreateDto.getTotalPrice());
      member.updateMoney(member.getMoney() - orderCreateDto.getTotalPrice());
      if (memberDao.update(member, session) == 0) {
        throw new CustomException("회원 잔고 업데이트 오류");
      }

      /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
      if (isCouponUsed(orderCreateDto.getCouponId())) {
        Coupon coupon =
            couponDao.selectById(orderCreateDto.getCouponId(), session).orElseThrow(Exception::new);
        coupon.updateStatus(CouponStatus.USED.name());
        if (couponDao.update(coupon, session) == 0) {
          throw new CustomException("쿠폰 상태 업데이트 오류");
        }
      }

      /* 상품 주문 orders, product_order, payment, delivery 생성 */
      Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
      orderDao.insert(order, session);

      Delivery delivery =
          Delivery.builder()
              .orderId(order.getId())
              .roadName(orderCreateDto.getAddress().getRoadName())
              .addrDetail(orderCreateDto.getAddress().getAddrDetail())
              .zipCode(orderCreateDto.getAddress().getZipCode())
              .status(DeliveryStatus.PENDING.name())
              .build();
      deliveryDao.insert(delivery, session);

      Payment payment =
          Payment.builder()
              .orderId(order.getId())
              .type(PaymentType.CASH.name())
              .actualAmount(orderCreateDto.getTotalPrice())
              .build();
      paymentDao.insert(payment, session);

      session.commit();

      return order;
    } catch (CustomException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  // TODO: 장바구니 상품 주문
  public Order createCartOrder(CartOrderCreateDto cartOrderCreateDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      Long memberId = cartOrderCreateDto.getMemberId();
      /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
      cartOrderCreateDto
          .getProducts()
          .forEach(
              p -> {
                try {
                  Product product =
                      productDao.selectById(p.getProductId(), session).orElseThrow(Exception::new);
                  validateEnoughStockQuantity(product.getQuantity(), p.getQuantity());
                  product.updateQuantity(product.getQuantity() - p.getQuantity());
                  if (productDao.update(product, session) == 0) {
                    throw new CustomException("상품 수량 업데이트 오류");
                  }

                  /* 장바구니에서 상품들 제거 */
                  int deletedRow =
                      cartDao.deleteById(
                          ProductAndMemberCompositeKey.builder()
                              .memberId(memberId)
                              .productId(product.getId())
                              .build(),
                          session);
                  if (deletedRow == 0) {
                    throw new CustomException("장바구니 상품 삭제 오류");
                  }

                } catch (Exception e) {
                  throw new RuntimeException(e);
                }
              });

      /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
      Member member =
          memberDao
              .selectById(cartOrderCreateDto.getMemberId(), session)
              .orElseThrow(Exception::new);
      validateEnoughMoney(member.getMoney(), cartOrderCreateDto.getTotalPrice());
      member.updateMoney(member.getMoney() - cartOrderCreateDto.getTotalPrice());
      if (memberDao.update(member, session) == 0) {
        throw new CustomException("회원 잔고 업데이트 오류");
      }

      /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
      if (isCouponUsed(cartOrderCreateDto.getCouponId())) {
        Coupon coupon =
            couponDao
                .selectById(cartOrderCreateDto.getCouponId(), session)
                .orElseThrow(Exception::new);
        coupon.updateStatus(CouponStatus.USED.name());
        if (couponDao.update(coupon, session) == 0) {
          throw new CustomException("쿠폰 상태 업데이트 오류");
        }
      }

      /* 상품 주문 orders, product_order, payment, delivery 생성 */
      Order order = Order.builder().memberId(memberId).status(OrderStatus.PENDING.name()).build();
      orderDao.insert(order, session);

      Delivery delivery =
          Delivery.builder()
              .orderId(order.getId())
              .roadName(cartOrderCreateDto.getAddress().getRoadName())
              .addrDetail(cartOrderCreateDto.getAddress().getAddrDetail())
              .zipCode(cartOrderCreateDto.getAddress().getZipCode())
              .status(DeliveryStatus.PENDING.name())
              .build();
      deliveryDao.insert(delivery, session);

      Payment payment =
          Payment.builder()
              .orderId(order.getId())
              .type(PaymentType.CASH.name())
              .actualAmount(cartOrderCreateDto.getTotalPrice())
              .build();
      paymentDao.insert(payment, session);

      session.commit();

      return order;
    } catch (CustomException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  private void validateEnoughMoney(Long money, Long price) throws Exception {
    if (money - price < 0) {
      throw new Exception("회원의 잔액이 부족합니다.");
    }
  }

  private void validateEnoughStockQuantity(Long stockQuantity, Long buyQuantity) throws Exception {
    if (stockQuantity - buyQuantity < 0) {
      throw new Exception("상품의 재고가 부족합니다.");
    }
  }

  // TODO: 상품 주문 취소
  public void cancelOrder(Long orderId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 주문 정보 정보를 취소 상태로 바꿈 */
      Order order = orderDao.selectById(orderId, session).orElseThrow(Exception::new);
      if (order.getStatus().equals(OrderStatus.CANCELED.name())) {
        throw new Exception("이미 취소된 주문입니다.");
      }
      order.updateStatus(OrderStatus.CANCELED.name());
      if (orderDao.update(order, session) == 0) {
        throw new CustomException("주문 상태 업데이트 오류");
      }

      /* 배송 상태를 확인 1. 배송중이면 취소 불가 2. 배송중이 아니라면 배송 정보를 취소 상태로 바꾼다 */
      Delivery delivery = deliveryDao.selectById(orderId, session).orElseThrow(Exception::new);
      if (delivery.getStatus().equals(DeliveryStatus.CANCELED.name())) {
        throw new Exception("이미 취소된 배송입니다.");
      }
      if (isDeliveryProcessing(delivery.getStatus())) {
        throw new Exception("이미 배송중인 주문은 취소가 불가능합니다.");
      }

      // 배송중이 아니라면 배송 취소상태로 변경
      delivery.updateStatus(DeliveryStatus.CANCELED.name());
      if (deliveryDao.update(delivery, session) == 0) {
        throw new CustomException("배송 상태 업데이트 오류");
      }

      /* 사용했던 회원의 쿠폰이 있다면 쿠폰의 상태를 UNUSED로 바꿈 */
      if (isCouponUsed(order.getCouponId())) {
        Coupon coupon =
            couponDao.selectById(order.getCouponId(), session).orElseThrow(Exception::new);
        coupon.updateStatus(CouponStatus.UNUSED.name());
        if (couponDao.update(coupon, session) == 0) {
          throw new CustomException("쿠폰 상태 업데이트 오류");
        }
      }

      /* 회원의 보유 금액을 실제 결제 금액에 비례하여 증가시킴 */
      Payment payment = paymentDao.selectByOrderId(orderId, session).orElseThrow(Exception::new);
      Member member =
          memberDao.selectById(order.getMemberId(), session).orElseThrow(Exception::new);
      member.updateMoney(member.getMoney() + payment.getActualAmount());
      if (memberDao.update(member, session) == 0) {
        throw new CustomException("회원 잔고 업데이트 오류");
      }

      /* 취소한 상품들에 대한 수량을 증가시킴 */
      List<ProductOrder> productOrders = productOrderDao.selectAllByOrderId(orderId, session);
      productOrders.forEach(
          po -> {
            try {
              Product product =
                  productDao.selectById(po.getProductId(), session).orElseThrow(Exception::new);
              product.updateQuantity(product.getQuantity() + po.getQuantity());
              if (productDao.update(product, session) == 0) {
                throw new CustomException("상품 수량 업데이트 오류");
              }
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });

      session.commit();
    } catch (CustomException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  private static boolean isDeliveryProcessing(String status) {
    return status.equals(DeliveryStatus.PROCESSING.name());
  }

  private static boolean isCouponUsed(Long couponId) {
    return couponId != null;
  }

  /* 회원의 1년내의 주문 목록들을 최신순으로 조회 */
  public List<ProductOrderDto> getProductOrdersForMemberCurrentYear(Long memberId)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    List<ProductOrderDto> productOrderDtos;
    try {
      productOrderDtos = orderDao.selectProductOrdersForMemberCurrentYear(memberId, session);
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }

    return productOrderDtos;
  }

  /* 회원의 상세 주문을 조회 */
  public ProductOrderDetailDto getOrderDetailsForMemberAndOrderId(Long orderId, Long memberId)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    ProductOrderDetailDto productOrderDetailDto;
    try {
      final Map<String, Long> orderIdAndMemberIdParameterMap = new HashMap<>();
      orderIdAndMemberIdParameterMap.put("orderId", orderId);
      orderIdAndMemberIdParameterMap.put("memberId", memberId);
      productOrderDetailDto =
          orderDao
              .selectOrderDetailsForMemberAndOrderId(orderIdAndMemberIdParameterMap, session)
              .orElseThrow(Exception::new);
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }

    return productOrderDetailDto;
  }
}
