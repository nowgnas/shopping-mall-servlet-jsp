package app.service.order;

import app.dto.OrderProcessDto;
import app.error.ErrorCode;
import java.util.Map;

public interface OrderProcessableChecker {
  public Map<ErrorCode, Boolean> process(OrderProcessDto orderProcessDto);
}
