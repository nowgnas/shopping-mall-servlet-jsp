package app.enums;

import lombok.Getter;

@Getter
public enum CouponPolicy {
  CASH("가격"),
  DISCOUNT("퍼센트");

  private final String message;

  CouponPolicy(String message) {
    this.message = message;
  }
}
