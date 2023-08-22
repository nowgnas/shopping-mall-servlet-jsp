package app.error.exception.product;

import app.error.CustomException;

public class ProductNotFoundException extends CustomException {
private static final String message ="상품을 찾을 수 없습니다.";

  public ProductNotFoundException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return 404;
  }
}
