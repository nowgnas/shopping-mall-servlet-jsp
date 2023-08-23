package app.exception.cart;

import app.exception.CustomException;
import app.exception.ErrorCode;

public class ProductIsNotExistedInCartException extends CustomException{

  public ProductIsNotExistedInCartException(ErrorCode errorCode) {
    super(errorCode);
  }
}
