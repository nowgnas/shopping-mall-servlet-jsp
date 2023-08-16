package app.enums;

import lombok.Getter;

@Getter
public enum CouponStatus {
  YET("사용 안함"),
  USED("사용됨"),
  EXPIRED("기간 만료");

  private final String message;

  CouponStatus(String message) {
    this.message = message;
  }
}
