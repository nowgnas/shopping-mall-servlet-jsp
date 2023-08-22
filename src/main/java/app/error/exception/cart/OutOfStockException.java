package app.error.exception.cart;

import app.error.CustomException;
import app.error.ErrorCode;

public class OutOfStockException extends CustomException {

  public OutOfStockException(ErrorCode errorCode) {
    super(errorCode);
  }

}

