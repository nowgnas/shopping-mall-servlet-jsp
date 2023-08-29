package app.service.order.manager;

import app.dao.order.OrderDao;
import app.dto.order.response.ProductOrderDetailDto;
import app.dto.order.response.ProductOrderDto;
import app.entity.Order;
import app.enums.OrderStatus;
import app.exception.order.OrderAlreadyCanceledException;
import app.exception.order.OrderEntityNotFoundException;
import app.exception.order.OrderUpdateStatusException;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {

  private final OrderDao orderDao = new OrderDao();

  public Order determineOrder(Long orderId, SqlSession session) throws Exception {
    return orderDao.selectById(orderId, session).orElseThrow(OrderEntityNotFoundException::new);
  }

  public int createOrder(Order order, SqlSession session) throws Exception {
    return orderDao.insert(order, session);
  }

  public void checkAlreadyOrdered(Order order) {
    if (order.getStatus().equals(OrderStatus.CANCELED.name())) {
      throw new OrderAlreadyCanceledException();
    }
  }

  public void updateOrderStatus(Order order, OrderStatus status, SqlSession session)
      throws Exception {
    order.updateStatus(status.name());
    if (orderDao.update(order, session) == 0) {
      throw new OrderUpdateStatusException();
    }
  }

  public List<ProductOrderDto> getProductOrdersForMemberCurrentYear(
      Long memberId, SqlSession session) throws Exception {
    return orderDao.selectProductOrdersForMemberCurrentYear(memberId, session);
  }

  public ProductOrderDetailDto getOrderDetailsForMemberAndOrderId(
      Long orderId, Long memberId, SqlSession session) throws Exception {
    final Map<String, Long> orderIdAndMemberIdParameterMap = new HashMap<>();
    orderIdAndMemberIdParameterMap.put("orderId", orderId);
    orderIdAndMemberIdParameterMap.put("memberId", memberId);
    return orderDao
        .selectOrderDetailsForMemberAndOrderId(orderIdAndMemberIdParameterMap, session)
        .orElseThrow(OrderEntityNotFoundException::new);
  }
}
