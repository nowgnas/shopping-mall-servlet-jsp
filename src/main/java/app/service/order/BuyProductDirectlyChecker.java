package app.service.order;

import app.dto.OrderProcessDto;
import app.exception.ErrorCode;
import java.util.HashMap;
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
public class BuyProductDirectlyChecker implements BuyableChecker {

  Map<ErrorCode,Boolean> errorMessageBooleanMap = new HashMap<>();
  @Override
  public Map<ErrorCode, Boolean> process(OrderProcessDto orderProcessDto) {

  if (!this.isAffordable(orderProcessDto)) {
      errorMessageBooleanMap.put(ErrorCode.CUSTOMER_IS_NOT_AFFORDABLE, Boolean.FALSE);
    }
    if (!this.isQuantitySufficient(orderProcessDto)) {
      errorMessageBooleanMap.put(ErrorCode.QUANTITY_IS_NOT_SUFFICIENT, Boolean.FALSE);
    }
    return errorMessageBooleanMap;

}

  @Override
  public boolean isAffordable(OrderProcessDto orderProcessDto) {
    return false;
  }

  @Override
  public boolean isQuantitySufficient(OrderProcessDto orderProcessDto) {
    return false;
  }

  @Override
  public boolean isDeliverable(OrderProcessDto orderProcessDto) {
    return false;
  }
}
