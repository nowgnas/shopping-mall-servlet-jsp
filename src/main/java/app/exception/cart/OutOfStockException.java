package app.exception.cart;

import app.exception.DomainException;

public class OutOfStockException extends DomainException {

  private static final String errorMessage = "상품의 재고가 없습니다";

  public OutOfStockException() {
    super(errorMessage);
  }

  @Override
  public int getStatusCode() {
    return 400;
  }
}
