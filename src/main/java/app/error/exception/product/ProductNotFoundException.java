package app.error.exception.product;

import app.error.CustomException;
import app.error.ErrorCode;

public class ProductNotFoundException extends CustomException {

  public ProductNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
