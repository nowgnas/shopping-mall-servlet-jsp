package app.exception.cart;

import app.exception.DomainException;

public class CartNotFoundException extends DomainException {
    private static final String errorMessage = "존재하지 않는 장바구니 입니다.";
  public CartNotFoundException() {
    super(errorMessage);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
