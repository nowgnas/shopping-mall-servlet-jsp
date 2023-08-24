package app.service.order;

import app.dao.cart.CartDaoImpl;
import app.dao.coupon.CouponDao;
import app.dao.delivery.DeliveryDao;
import app.dao.member.MemberDao;
import app.dao.order.OrderDao;
import app.dao.payment.PaymentDao;
import app.dao.product.ProductDao;
import app.dao.productorder.ProductOrderDao;
import app.dto.cart.CartAndProductDto;
import app.dto.order.form.OrderCartCreateForm;
import app.dto.order.form.OrderCreateForm;
import app.dto.order.request.OrderCartCreateDto;
import app.dto.order.request.OrderCreateDto;
import app.dto.order.response.ProductOrderDetailDto;
import app.dto.order.response.ProductOrderDto;
import app.entity.ProductAndMemberCompositeKey;
import app.dto.product.response.ProductDetailForOrder;
import app.dto.response.OrderMemberDetail;
import app.entity.*;
import app.enums.CouponStatus;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import app.exception.EntityNotFoundException;
import app.exception.coupon.CouponEntityNotFoundException;
import app.exception.delivery.DeliveryEntityNotFoundException;
import app.exception.member.MemberEntityNotFoundException;
import app.exception.order.*;
import app.exception.payment.PaymentEntityNotFoundException;
import app.exception.product.ProductEntityNotFoundException;
import app.utils.GetSessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

public class OrderServiceImpl {

  private Logger log = Logger.getLogger("order");
  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private final OrderDao orderDao = new OrderDao();
  private final DeliveryDao deliveryDao = new DeliveryDao();
  private final PaymentDao paymentDao = new PaymentDao();
  private final CouponDao couponDao = new CouponDao();
  private final MemberDao memberDao = new MemberDao();
  private final ProductOrderDao productOrderDao = new ProductOrderDao();
  private final ProductDao productDao = ProductDao.getInstance();
  private final CartDaoImpl cartDao = new CartDaoImpl();

  /* 상품 주문 폼 */
  public OrderCreateForm getCreateOrderForm(Long memberId, Long productId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      ProductDetailForOrder productDetail = productDao.selectProductDetail(productId, session);
      OrderMemberDetail orderMemberDetail =
          memberDao
              .selectAddressAndCouponById(memberId, session)
              .orElseThrow(MemberEntityNotFoundException::new);

      return OrderCreateForm.of(orderMemberDetail, productDetail);
    } catch (EntityNotFoundException ex) {
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
  public Order createOrder(OrderCreateDto orderCreateDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
      Product product =
          productDao
              .selectById(orderCreateDto.getProductId(), session)
              .orElseThrow(ProductEntityNotFoundException::new);
      validateEnoughStockQuantity(product.getQuantity(), orderCreateDto.getQuantity());
      product.updateQuantity(product.getQuantity() - orderCreateDto.getQuantity());
      if (productDao.update(product, session) == 0) {
        throw new OrderProductUpdateStockQuantityException();
      }

      /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
      Member member =
          memberDao
              .selectById(orderCreateDto.getMemberId(), session)
              .orElseThrow(MemberEntityNotFoundException::new);
      validateEnoughMoney(member.getMoney(), orderCreateDto.getTotalPrice());
      member.updateMoney(member.getMoney() - orderCreateDto.getTotalPrice());
      if (memberDao.update(member, session) == 0) {
        throw new OrderMemberUpdateMoneyException();
      }

      /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
      if (isCouponUsed(orderCreateDto.getCouponId())) {
        Coupon coupon =
            couponDao
                .selectById(orderCreateDto.getCouponId(), session)
                .orElseThrow(CouponEntityNotFoundException::new);
        coupon.updateStatus(CouponStatus.USED.name());
        if (couponDao.update(coupon, session) == 0) {
          throw new OrderCouponUpdateStatusException();
        }
      }

      /* 상품 주문 Order, ProductOrder, Payment, Delivery 생성 */
      Order order = orderCreateDto.toOrderEntity();
      orderDao.insert(order, session);

      ProductOrder productOrder = orderCreateDto.toProductOrderEntity(order.getId());
      productOrderDao.insert(productOrder, session);

      Delivery delivery = orderCreateDto.toDeliveryEntity(order.getId());
      deliveryDao.insert(delivery, session);

      Payment payment = orderCreateDto.toPaymentEntity(order.getId());
      paymentDao.insert(payment, session);

      session.commit();

      return order;
    } catch (EntityNotFoundException ex) {
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
  public OrderCartCreateForm getCreateCartOrderForm(Long memberId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 회원 정보 조회 */
      OrderMemberDetail orderMemberDetail =
          memberDao
              .selectAddressAndCouponById(memberId, session)
              .orElseThrow(MemberEntityNotFoundException::new);
      /* 회원으로 장바구니에 들어있는 상품들 모두 조회 */
      List<CartAndProductDto> cartAndProductDtos =
          cartDao.getAllCartsAndAllProductsByMember(memberId, session);

      return OrderCartCreateForm.of(orderMemberDetail, cartAndProductDtos);
    } catch (EntityNotFoundException ex) {
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
  public Order createCartOrder(OrderCartCreateDto orderCartCreateDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      Long memberId = orderCartCreateDto.getMemberId();
      List<CartAndProductDto> cartAndProductDtos =
          cartDao.getAllCartsAndAllProductsByMember(memberId, session);
      orderCartCreateDto.setProducts(cartAndProductDtos);
      /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 재고 차감 */
      List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys = new ArrayList<>();
      orderCartCreateDto
          .getProducts()
          .forEach(
              p -> {
                try {
                  Product product =
                      productDao
                          .selectById(p.getProductId(), session)
                          .orElseThrow(ProductEntityNotFoundException::new);
                  validateEnoughStockQuantity(product.getQuantity(), p.getQuantity());
                  product.updateQuantity(product.getQuantity() - p.getQuantity());
                  if (productDao.update(product, session) == 0) {
                    throw new OrderProductUpdateStockQuantityException();
                  }

                  productAndMemberCompositeKeys.add(ProductAndMemberCompositeKey.builder()
                          .memberId(memberId)
                          .productId(product.getId())
                          .build());

                } catch (Exception e) {
                  throw new RuntimeException(e);
                }
              });

      /* 장바구니 벌크 삭제 */
      int deletedRow = cartDao.bulkDelete(productAndMemberCompositeKeys, session);
      if(deletedRow != productAndMemberCompositeKeys.size()) {
        throw new OrderCartDeleteException();
      }

      /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */
      Member member =
          memberDao
              .selectById(orderCartCreateDto.getMemberId(), session)
              .orElseThrow(MemberEntityNotFoundException::new);
      validateEnoughMoney(member.getMoney(), orderCartCreateDto.getTotalPrice());
      member.updateMoney(member.getMoney() - orderCartCreateDto.getTotalPrice());
      if (memberDao.update(member, session) == 0) {
        throw new OrderMemberUpdateMoneyException();
      }

      /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */
      if (isCouponUsed(orderCartCreateDto.getCouponId())) {
        Coupon coupon =
            couponDao
                .selectById(orderCartCreateDto.getCouponId(), session)
                .orElseThrow(CouponEntityNotFoundException::new);
        coupon.updateStatus(CouponStatus.USED.name());
        if (couponDao.update(coupon, session) == 0) {
          throw new OrderCouponUpdateStatusException();
        }
      }

      /* 상품 주문 orders, product_order, payment, delivery 생성 */
      Order order = orderCartCreateDto.toOrderEntity();
      orderDao.insert(order, session);

      List<ProductOrder> productOrders = orderCartCreateDto.toProductOrderEntities(order.getId());
      productOrderDao.bulkInsert(productOrders, session);

      Delivery delivery = orderCartCreateDto.toDeliveryEntity(order.getId());
      deliveryDao.insert(delivery, session);

      Payment payment = orderCartCreateDto.toPaymentEntity(order.getId());
      paymentDao.insert(payment, session);

      session.commit();

      return order;
    } catch (EntityNotFoundException ex) {
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

  private void validateEnoughMoney(Long money, Long price) {
    if (money - price < 0) {
      throw new OrderMemberNotEnoughMoneyException();
    }
  }

  private void validateEnoughStockQuantity(Long stockQuantity, Long buyQuantity) {
    if (stockQuantity - buyQuantity < 0) {
      throw new OrderProductNotEnoughStockQuantityException();
    }
  }

  /* 상품 주문 취소 */
  public void cancelOrder(Long orderId) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 주문 정보 정보를 취소 상태로 바꿈 */
      Order order =
          orderDao.selectById(orderId, session).orElseThrow(OrderEntityNotFoundException::new);
      if (order.getStatus().equals(OrderStatus.CANCELED.name())) {
        throw new OrderAlreadyCanceledException();
      }
      order.updateStatus(OrderStatus.CANCELED.name());
      if (orderDao.update(order, session) == 0) {
        throw new OrderUpdateStatusException();
      }

      /* 배송 상태를 확인 1. 배송중이면 취소 불가 2. 배송중이 아니라면 배송 정보를 취소 상태로 바꾼다 */
      Delivery delivery =
          deliveryDao
              .selectById(orderId, session)
              .orElseThrow(DeliveryEntityNotFoundException::new);
      if (delivery.getStatus().equals(DeliveryStatus.CANCELED.name())) {
        throw new OrderDeliveryAlreadyCanceledException();
      }
      if (isDeliveryProcessing(delivery.getStatus())) {
        throw new OrderDeliveryProcessingException();
      }

      // 배송중이 아니라면 배송 취소상태로 변경
      delivery.updateStatus(DeliveryStatus.CANCELED.name());
      if (deliveryDao.update(delivery, session) == 0) {
        throw new OrderDeliveryUpdateStatusException();
      }

      /* 사용했던 회원의 쿠폰이 있다면 쿠폰의 상태를 UNUSED로 바꿈 */
      if (isCouponUsed(order.getCouponId())) {
        Coupon coupon =
            couponDao
                .selectById(order.getCouponId(), session)
                .orElseThrow(CouponEntityNotFoundException::new);
        coupon.updateStatus(CouponStatus.UNUSED.name());
        if (couponDao.update(coupon, session) == 0) {
          throw new OrderCouponUpdateStatusException();
        }
      }

      /* 회원의 보유 금액을 실제 결제 금액에 비례하여 증가시킴 */
      Payment payment =
          paymentDao
              .selectByOrderId(orderId, session)
              .orElseThrow(PaymentEntityNotFoundException::new);
      Member member =
          memberDao
              .selectById(order.getMemberId(), session)
              .orElseThrow(MemberEntityNotFoundException::new);
      member.updateMoney(member.getMoney() + payment.getActualAmount());
      if (memberDao.update(member, session) == 0) {
        throw new OrderMemberUpdateMoneyException();
      }

      /* 취소한 상품들에 대한 수량을 증가시킴 */
      List<ProductOrder> productOrders = productOrderDao.selectAllByOrderId(orderId, session);
      productOrders.forEach(
          po -> {
            try {
              Product product =
                  productDao
                      .selectById(po.getProductId(), session)
                      .orElseThrow(ProductEntityNotFoundException::new);
              product.updateQuantity(product.getQuantity() + po.getQuantity());
              if (productDao.update(product, session) == 0) {
                throw new OrderProductUpdateStockQuantityException();
              }
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          });

      session.commit();
    } catch (EntityNotFoundException ex) {
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
              .orElseThrow(OrderEntityNotFoundException::new);
    } catch (EntityNotFoundException ex) {
      log.error(ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }

    return productOrderDetailDto;
  }
}
