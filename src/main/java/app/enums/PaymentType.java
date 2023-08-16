package app.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
  CASH("현금"),
  CARD("카드");

  private final String message;

  PaymentType(String message) {
    this.message = message;
  }
}
