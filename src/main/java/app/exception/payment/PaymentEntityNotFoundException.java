package app.exception.payment;

import app.exception.EntityNotFoundException;

public class PaymentEntityNotFoundException extends EntityNotFoundException {

  private static final String message = "결제 정보를 찾을 수 없습니다.";

  public PaymentEntityNotFoundException() {
    super(message);
  }
}
