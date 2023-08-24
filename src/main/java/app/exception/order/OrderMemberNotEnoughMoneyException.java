package app.exception.order;

import app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

/* 주문할 때 회원의 잔액 부족 에러 */
public class OrderMemberNotEnoughMoneyException extends DomainException {

  private static final String message = "회원의 잔액이 부족합니다.";

  public OrderMemberNotEnoughMoneyException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
