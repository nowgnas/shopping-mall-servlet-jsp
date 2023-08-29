package app.exception.cart;

import app.exception.CustomException;
import app.exception.ErrorCode;

public class OutOfStockException extends CustomException {

  public OutOfStockException(ErrorCode errorCode) {
    super(errorCode);
  }
}
