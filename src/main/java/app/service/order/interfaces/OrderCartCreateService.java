package app.service.order.interfaces;

import app.dto.order.form.OrderCartCreateForm;
import app.dto.order.request.OrderCartCreateDto;
import app.entity.Order;

public interface OrderCartCreateService {

  OrderCartCreateForm getCreateCartOrderForm(Long memberId) throws Exception;

  Order createCartOrder(OrderCartCreateDto orderCartCreateDto) throws Exception;
}
