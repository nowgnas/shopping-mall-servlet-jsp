package app.exception.cart;

import app.error.ErrorCode;

public class OutOfStockException extends CartException {

  public OutOfStockException(ErrorCode errorCode) {
    super(errorCode);
  }
}
