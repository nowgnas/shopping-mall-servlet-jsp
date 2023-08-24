package app.error.exception.product;

import app.error.CustomException;
import app.error.ErrorCode;
import java.util.function.Supplier;

public class ProductNotFoundException extends CustomException {

  public ProductNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
