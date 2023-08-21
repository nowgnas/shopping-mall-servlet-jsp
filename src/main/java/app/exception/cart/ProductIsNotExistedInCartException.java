package app.exception.cart;

import app.error.ErrorCode;

public class ProductIsNotExistedInCartException extends CartException {

  public ProductIsNotExistedInCartException(ErrorCode errorCode) {
    super(errorCode);
  }
}
