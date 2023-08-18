package app.service.order;

import app.dao.delivery.DeliveryDao;
import app.dao.exception.CustomException;
import app.dao.member.MemberDao;
import app.dao.order.OrderDao;
import app.dao.payment.PaymentDao;
import app.dto.request.OrderCancelDto;
import app.dto.request.OrderCreateDto;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.Delivery;
import app.entity.Order;
import app.entity.Payment;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import app.enums.PaymentType;
import app.utils.GetSessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class OrderServiceImpl {

  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private final OrderDao orderDao = new OrderDao();
  private final DeliveryDao deliveryDao = new DeliveryDao();
  private final PaymentDao paymentDao = new PaymentDao();
  private final MemberDao memberDao = new MemberDao();

  //TODO: 상품 주문
  public void createOrder(OrderCreateDto orderCreateDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      Long memberId = orderCreateDto.getMemberId();
      /* 상품 재고 확인 1. 없다면 구매 불가 2. 있다면 패스 */

      /* 회원의 잔액 확인 1. 총 상품 가격보다 잔액이 적다면 구매 불가 2. 잔액이 충분하다면 회원의 잔액에서 차감 */

      /* 회원이 쿠폰을 썼는지 확인 1. 쿠폰을 적용했다면 회원의 쿠폰 정보 '사용됨' 상태로 바꿈 2. 쿠폰을 적용하지 않았다면 패스 */

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
    } catch (CustomException ex) {
      session.rollback();
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  //TODO: 상품 주문 취소
  public void cancelOrder(OrderCancelDto orderCancelDto) throws Exception {
    SqlSession session = sessionFactory.openSession();
    try {
      /* 배송 상태를 확인 1. 배송중이면 취소 불가 2. 배송중이 아니라면 배송 정보를 취소 상태로 바꾼다 */

      /* 사용했던 회원의 쿠폰이 있다면 쿠폰의 상태를 UNUSED로 바꿈 */

      /* 회원의 보유 금액을 실제 결제 금액에 비례하여 증가시킴 */

      /* 취소한 상품들에 대한 수량을 증가시킴 */

      /* 주문 정보 정보를 취소 상태로 바꿈 */

      session.commit();
    } catch (CustomException ex) {
      session.rollback();
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
      session.rollback();
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }
  }

  /* 회원의 1년내의 주문 목록들을 최신순으로 조회 */
  public List<ProductOrderDto> getProductOrdersForMemberCurrentYear(Long memberId)
      throws Exception {
    SqlSession session = sessionFactory.openSession();
    List<ProductOrderDto> productOrderDtos;
    try {
      productOrderDtos = orderDao.selectProductOrdersForMemberCurrentYear(memberId, session);
    } catch (CustomException ex) {
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
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
              .get();
    } catch (CustomException ex) {
      throw new CustomException(ex.getMessage());
    } catch (Exception ex) {
      throw new Exception(ex.getMessage());
    } finally {
      session.close();
    }

    return productOrderDetailDto;
  }
}
