package app.error.exception.cart;


import app.exception.CustomException;
import app.exception.ErrorCode;

public class CartNotFoundException extends CustomException {

  public CartNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
