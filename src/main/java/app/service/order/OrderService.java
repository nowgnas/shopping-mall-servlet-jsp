package app.service.order;

import app.dto.OrderProcessDto;
import app.error.ErrorCode;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

  Optional<Map<ErrorCode, Boolean>> buy(OrderProcessDto orderProcessDto);

  Optional<Map<ErrorCode, Boolean>> cancel(OrderProcessDto orderProcessDto);

}
