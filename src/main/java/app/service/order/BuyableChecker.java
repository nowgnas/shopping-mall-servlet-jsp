package app.service.order;

import app.dto.OrderProcessDto;

public interface BuyableChecker extends OrderProcessableChecker {
  boolean isAffordable(OrderProcessDto orderProcessDto);
  boolean isQuantitySufficient(OrderProcessDto orderProcessDto);
  boolean isDeliverable(OrderProcessDto orderProcessDto);
}
