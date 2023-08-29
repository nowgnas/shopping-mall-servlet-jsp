package app.exception.cart;

import app.exception.CustomException;
import app.exception.ErrorCode;

public class CartQuantityIsUnder0Exception extends CustomException {

  public CartQuantityIsUnder0Exception(ErrorCode errorCode) {
    super(errorCode);
  }
}
