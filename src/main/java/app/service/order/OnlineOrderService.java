package app.service.order;

import app.dto.OrderProcessDto;
import app.exception.ErrorCode;
import app.utils.OrderValidator;
import java.util.Map;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OnlineOrderService implements OrderService {

  private  OrderValidator orderValidator;

  @Override
  public Optional<Map<ErrorCode, Boolean>> buy(OrderProcessDto orderProcessDto) {
    return Optional.of(orderValidator.validate(orderProcessDto));
  }

  @Override
  public  Optional<Map<ErrorCode, Boolean>>  cancel(OrderProcessDto orderProcessDto) {
    return Optional.of(orderValidator.validate(orderProcessDto));
  }
}
