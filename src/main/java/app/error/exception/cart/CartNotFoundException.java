package app.error.exception.cart;

import app.error.CustomException;
import app.error.ErrorCode;

public class CartNotFoundException extends CustomException {

  public CartNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
