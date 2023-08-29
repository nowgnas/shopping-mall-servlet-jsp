package app.service.order.interfaces;

import app.dto.order.form.OrderCreateForm;
import app.dto.order.request.OrderCreateDto;
import app.entity.Order;

public interface OrderCreateService {
  OrderCreateForm getCreateOrderForm(Long memberId, Long productId) throws Exception;

  Order createOrder(OrderCreateDto orderCreateDto) throws Exception;
}
