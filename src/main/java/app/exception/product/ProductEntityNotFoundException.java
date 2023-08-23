package app.exception.product;

import app.exception.EntityNotFoundException;

public class ProductEntityNotFoundException extends EntityNotFoundException {

  private static final String message = "상품 정보를 찾을 수 없습니다.";

  public ProductEntityNotFoundException() {
    super(message);
  }
}
