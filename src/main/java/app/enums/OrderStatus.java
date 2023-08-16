package app.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
  PENDING("주문 준비중"),
  PROCESSING("주문 접수중"),
  COMPLETED("주문 접수"),
  CANCELED("주문 취소");

  private final String message;

  OrderStatus(String message) {
    this.message = message;
  }
}
