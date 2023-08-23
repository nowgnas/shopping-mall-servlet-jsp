package app.exception.order;

import app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

/* 주문 취소할 때 이미 취소된 배송일 경우 에러 */
public class OrderDeliveryAlreadyCanceledException extends DomainException {

  private static final String message = "이미 취소된 배송입니다.";

  public OrderDeliveryAlreadyCanceledException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
