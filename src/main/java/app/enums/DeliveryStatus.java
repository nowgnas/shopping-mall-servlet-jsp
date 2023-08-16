package app.enums;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
  PENDING("배송 준비중"),
  PROCESSING("배송중"),
  COMPLETED("배송 완료"),
  CANCELED("배송 취소");

  private final String message;

  DeliveryStatus(String message) {
    this.message = message;
  }
}
