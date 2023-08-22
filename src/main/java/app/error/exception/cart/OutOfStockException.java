package app.error.exception.cart;

import app.error.CustomException;

public class OutOfStockException extends CustomException {

  private static final String message = "재고가 부족해 입력하신 개수만큼 장바구니에 상품을 담을 수 없습니다.";
  public OutOfStockException() {
    super(message);
  }
  @Override
  public int getStatusCode() {
   return 400;
  }
}

