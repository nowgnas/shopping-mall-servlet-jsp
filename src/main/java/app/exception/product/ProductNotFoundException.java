package app.exception.product;

import app.exception.CustomException;
import app.exception.ErrorCode;

public class ProductNotFoundException extends CustomException {

  public ProductNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
