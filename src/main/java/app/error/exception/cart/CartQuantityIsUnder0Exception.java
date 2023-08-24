package app.error.exception.cart;

import app.error.CustomException;
import app.error.ErrorCode;

public class CartQuantityIsUnder0Exception extends CustomException {
  public CartQuantityIsUnder0Exception(ErrorCode errorCode) {
    super(errorCode);
  }
}
