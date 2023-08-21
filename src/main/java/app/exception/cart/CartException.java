package app.exception.cart;

import app.error.ErrorCode;

abstract class CartException extends RuntimeException{

  public CartException(ErrorCode errorCode) {
    super(errorCode.getMessage());
  }

}
