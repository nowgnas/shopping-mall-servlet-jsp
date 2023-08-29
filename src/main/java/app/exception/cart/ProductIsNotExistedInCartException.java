package app.exception.cart;


import app.exception.EntityNotFoundException;

public class ProductIsNotExistedInCartException extends EntityNotFoundException {
    private static final String errorMessage = "상품이 장바구니에 존재하지 않습니다";
  public ProductIsNotExistedInCartException() {
    super(errorMessage);
  }
}
