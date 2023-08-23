package app.exception.coupon;

import app.exception.EntityNotFoundException;

public class CouponEntityNotFoundException extends EntityNotFoundException {

  private static final String message = "쿠폰 정보를 찾을 수 없습니다.";

  public CouponEntityNotFoundException() {
    super(message);
  }
}
