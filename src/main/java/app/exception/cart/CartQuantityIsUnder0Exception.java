package app.exception.cart;

import app.exception.DomainException;

public class CartQuantityIsUnder0Exception extends DomainException {
  private static final String errorMessage = "0개 이하의 상품을 담을 수 없습니다.";

  public CartQuantityIsUnder0Exception() {
    super(errorMessage);
  }

  @Override
  public int getStatusCode() {
    return 400;
  }
}
