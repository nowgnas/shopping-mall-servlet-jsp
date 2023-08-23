package app.exception.order;

import app.exception.EntityNotFoundException;

public class OrderEntityNotFoundException extends EntityNotFoundException {

  private static final String message = "주문 엔티티를 찾을 수 없습니다.";

  public OrderEntityNotFoundException() {
    super(message);
  }
}
