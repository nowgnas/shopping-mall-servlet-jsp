package app.service.order;

import app.dto.cart.CartAndProductDto;
import app.dto.order.form.OrderCartCreateForm;
import app.dto.order.form.OrderCreateForm;
import app.dto.order.request.OrderCartCreateDto;
import app.dto.order.request.OrderCreateDto;
import app.dto.order.response.ProductOrderDetailDto;
import app.dto.order.response.ProductOrderDto;
import app.dto.product.response.ProductDetailForOrder;
import app.dto.response.OrderMemberDetail;
import app.entity.*;
import app.enums.CouponStatus;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import app.exception.DomainException;
import app.service.order.interfaces.OrderCartCreateService;
import app.service.order.interfaces.OrderCreateService;
import app.service.order.interfaces.OrderDeleteService;
import app.service.order.interfaces.OrderReadService;
import app.service.order.manager.*;
import app.utils.GetSessionFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

public class OrderService
    implements OrderCreateService, OrderCartCreateService, OrderReadService, OrderDeleteService {

  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private final OrderManager orderManager = new OrderManager();
  private final OrderProductManager orderProductManager = new OrderProductManager();
  private final OrderMemberManager orderMemberManager = new OrderMemberManager();
  private final OrderCouponManager orderCouponManager = new OrderCouponManager();
  private final OrderProductOrderManager orderProductOrderManager = new OrderProductOrderManager();
  private final OrderDeliveryManager orderDeliveryManager = new OrderDeliveryManager();
  private final OrderPaymentManager orderPaymentManager = new OrderPaymentManager();
  private final OrderCartManager orderCartManager = new OrderCartManager();
  private Logger log = Logger.getLogger("order");

  /* 상품 주문 폼 */
  @Override
  public OrderCreateForm getCreateOrderForm(Long memberId, Long productId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      ProductDetailForOrder productDetail =
          orderProductManager.determineProductDetailForOrder(productId, session);
      OrderMemberDetail orderMemberDetail =
          orderMemberManager.determineOrderMemberDetail(memberId, session);

      return OrderCreateForm.of(orderMemberDetail, productDetail);
    } catch (DomainException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  /* 상품 주문 */
  @Override
  public Order createOrder(OrderCreateDto orderCreateDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
      Product product =
          orderProductManager.determineProduct(orderCreateDto.getProductId(), session);
      orderProductManager.validateEnoughStockQuantity(
          product.getQuantity(), orderCreateDto.getQuantity());
      orderProductManager.updateStockQuantity(
          product, product.getQuantity() - orderCreateDto.getQuantity(), session);

      /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
      Long couponId = orderCreateDto.getCouponId();
      if (orderCouponManager.isCouponUsed(couponId)) {
        Coupon coupon = orderCouponManager.determineCoupon(couponId, session);
        orderCouponManager.updateCouponStatus(coupon, CouponStatus.USED, session);
      }

      /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
      Member member = orderMemberManager.determineMember(orderCreateDto.getMemberId(), session);
      orderMemberManager.validateEnoughMoney(member.getMoney(), orderCreateDto.getTotalPrice());
      orderMemberManager.updateMemberMoney(
          member, member.getMoney() - orderCreateDto.getTotalPrice(), session);

      /* 상품 주문 Order, ProductOrder, Payment, Delivery 생성 */
      Order order = orderCreateDto.toOrderEntity();
      orderManager.createOrder(order, session);

      ProductOrder productOrder = orderCreateDto.toProductOrderEntity(order.getId());
      orderProductOrderManager.createProductOrder(productOrder, session);

      Delivery delivery = orderCreateDto.toDeliveryEntity(order.getId());
      orderDeliveryManager.createDelivery(delivery, session);

      Payment payment = orderCreateDto.toPaymentEntity(order.getId());
      orderPaymentManager.createPayment(payment, session);

      session.commit();

      return order;

    } catch (DomainException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  /* 장바구니 상품 주문 폼 */
  @Override
  public OrderCartCreateForm getCreateCartOrderForm(Long memberId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 회원 정보 조회 */
      OrderMemberDetail orderMemberDetail =
          orderMemberManager.determineOrderMemberDetail(memberId, session);
      /* 회원으로 장바구니에 들어있는 상품들 모두 조회 */
      List<CartAndProductDto> cartAndProductDtos =
          orderCartManager.determineCartAndProductDtos(memberId, session);
      cartAndProductDtos.forEach(
          cp ->
              orderProductManager.validateEnoughStockQuantity(
                  cp.getProductQuantity(), cp.getCartProductQuantity()));

      return OrderCartCreateForm.of(orderMemberDetail, cartAndProductDtos);
    } catch (DomainException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  /* 장바구니 상품 주문 */
  @Override
  public Order createCartOrder(OrderCartCreateDto orderCartCreateDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      List<CartAndProductDto> cartAndProductDtos =
          orderCartManager.determineCartAndProductDtos(orderCartCreateDto.getMemberId(), session);
      orderCartCreateDto.setProducts(cartAndProductDtos);
      /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
      List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys = new ArrayList<>();
      orderCartCreateDto
          .getProducts()
          .forEach(
              p -> {
                try {
                  Product product = orderProductManager.determineProduct(p.getProductId(), session);
                  orderProductManager.validateEnoughStockQuantity(
                      product.getQuantity(), p.getQuantity());
                  orderProductManager.updateStockQuantity(
                      product, product.getQuantity() - p.getQuantity(), session);

                  productAndMemberCompositeKeys.add(
                      ProductAndMemberCompositeKey.builder()
                          .memberId(orderCartCreateDto.getMemberId())
                          .productId(product.getId())
                          .build());

                } catch (Exception e) {
                  throw new RuntimeException(e);
                }
              });

      /* 장바구니 벌크 삭제 */
      orderCartManager.deleteAll(productAndMemberCompositeKeys, session);

      /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
      Long couponId = orderCartCreateDto.getCouponId();
      if (orderCouponManager.isCouponUsed(couponId)) {
        Coupon coupon = orderCouponManager.determineCoupon(couponId, session);
        orderCouponManager.updateCouponStatus(coupon, CouponStatus.USED, session);
      }

      /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
      Member member = orderMemberManager.determineMember(orderCartCreateDto.getMemberId(), session);
      orderMemberManager.validateEnoughMoney(member.getMoney(), orderCartCreateDto.getTotalPrice());
      orderMemberManager.updateMemberMoney(
          member, member.getMoney() - orderCartCreateDto.getTotalPrice(), session);

      /* 상품 주문 orders, product_order, payment, delivery 생성 */
      Order order = orderCartCreateDto.toOrderEntity();
      orderManager.createOrder(order, session);

      List<ProductOrder> productOrders = orderCartCreateDto.toProductOrderEntities(order.getId());
      orderProductOrderManager.createProductOrders(productOrders, session);

      Delivery delivery = orderCartCreateDto.toDeliveryEntity(order.getId());
      orderDeliveryManager.createDelivery(delivery, session);

      Payment payment = orderCartCreateDto.toPaymentEntity(order.getId());
      orderPaymentManager.createPayment(payment, session);

      session.commit();

      return order;
    } catch (DomainException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  /* 상품 주문 취소 */
  @Override
  public void cancelOrder(Long orderId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 주문 정보 정보를 취소 상태로 바꿈 */
      Order order = orderManager.determineOrder(orderId, session);
      orderManager.checkAlreadyOrdered(order);
      orderManager.updateOrderStatus(order, OrderStatus.CANCELED, session);

      /* 배송 상태를 확인 1. 배송중이면 취소 불가 2. 배송중이 아니라면 배송 정보를 취소 상태로 바꾼다 */
      Delivery delivery = orderDeliveryManager.determineDelivery(orderId, session);
      orderDeliveryManager.checkDeliveryCanceled(delivery);
      orderDeliveryManager.checkDeliveryProcessing(delivery);

      // 배송중이 아니라면 배송 취소상태로 변경
      orderDeliveryManager.updateDeliveryStatus(delivery, DeliveryStatus.CANCELED, session);

      /* 사용했던 회원의 쿠폰이 있다면 쿠폰의 상태를 UNUSED로 바꿈 */
      Long couponId = order.getCouponId();
      if (orderCouponManager.isCouponUsed(couponId)) {
        Coupon coupon = orderCouponManager.determineCoupon(couponId, session);
        orderCouponManager.updateCouponStatus(coupon, CouponStatus.UNUSED, session);
      }

      /* 취소한 상품들에 대한 수량을 증가시킴 */
      List<ProductOrder> productOrders =
          orderProductOrderManager.determineProductOrdersByOrderId(orderId, session);
      productOrders.forEach(
          po -> {
            try {
              Product product = orderProductManager.determineProduct(po.getProductId(), session);
              orderProductManager.updateStockQuantity(
                  product, product.getQuantity() + po.getQuantity(), session);
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });

      /* 회원의 보유 금액을 실제 결제 금액에 비례하여 증가시킴 */
      Payment payment = orderPaymentManager.determinePaymentByOrderId(orderId, session);
      Member member = orderMemberManager.determineMember(order.getMemberId(), session);
      orderMemberManager.updateMemberMoney(
          member, member.getMoney() + payment.getActualAmount(), session);

      session.commit();
    } catch (DomainException ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  /* 회원의 1년내의 주문 목록들을 최신순으로 조회 */
  @Override
  public List<ProductOrderDto> getProductOrdersForMemberCurrentYear(Long memberId)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      return orderManager.getProductOrdersForMemberCurrentYear(memberId, session);
    } catch (DomainException ex) {
      log.error(ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  /* 회원의 상세 주문을 조회 */
  @Override
  public ProductOrderDetailDto getOrderDetailsForMemberAndOrderId(Long orderId, Long memberId)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      return orderManager.getOrderDetailsForMemberAndOrderId(orderId, memberId, session);
    } catch (DomainException ex) {
      log.error(ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }
}
