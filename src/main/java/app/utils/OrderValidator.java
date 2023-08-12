package app.utils;

import app.dto.OrderProcessDto;
import app.error.ErrorCode;
import app.service.order.OrderProcessableChecker;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderValidator {
    private OrderProcessableChecker orderProcessableChecker;
  public Map<ErrorCode,Boolean> validate(OrderProcessDto processDto){
        return orderProcessableChecker.process(processDto);
  }

}
