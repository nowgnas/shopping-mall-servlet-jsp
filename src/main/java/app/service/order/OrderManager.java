package app.service.order;

import app.dao.order.OrderDao;
import app.entity.Order;
import app.enums.OrderStatus;
import app.exception.order.OrderAlreadyCanceledException;
import app.exception.order.OrderEntityNotFoundException;
import app.exception.order.OrderUpdateStatusException;
import org.apache.ibatis.session.SqlSession;

public class OrderManager {

  private final OrderDao orderDao = new OrderDao();

  public Order determineOrder(Long orderId, SqlSession session) throws Exception {
    return orderDao.selectById(orderId, session).orElseThrow(OrderEntityNotFoundException::new);
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
}
