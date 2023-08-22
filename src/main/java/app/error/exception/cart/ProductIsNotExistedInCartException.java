package app.error.exception.cart;

import app.error.CustomException;
import app.error.ErrorCode;
import org.modelmapper.spi.ErrorMessage;

public class ProductIsNotExistedInCartException extends CustomException{
  private static final String message ="상품이 장바구니에 존재하지 않습니다.";
  protected ProductIsNotExistedInCartException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
