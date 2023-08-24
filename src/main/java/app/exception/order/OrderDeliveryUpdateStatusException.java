package app.exception.order;

import app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

/* 주문 취소할 때 배송 상태 업데이트시 에러 */
public class OrderDeliveryUpdateStatusException extends DomainException {

  private static final String message = "존재하지 않는 배송입니다.";

  public OrderDeliveryUpdateStatusException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
