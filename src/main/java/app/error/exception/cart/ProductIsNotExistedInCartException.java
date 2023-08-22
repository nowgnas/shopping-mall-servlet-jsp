package app.error.exception.cart;

import app.error.CustomException;
import app.error.ErrorCode;

public class ProductIsNotExistedInCartException extends CustomException{

  public ProductIsNotExistedInCartException(ErrorCode errorCode) {
    super(errorCode);
  }
}
