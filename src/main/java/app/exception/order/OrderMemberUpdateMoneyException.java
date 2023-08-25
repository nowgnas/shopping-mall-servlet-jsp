package app.exception.order;

import app.exception.DomainException;

import javax.servlet.http.HttpServletResponse;

/* 주문할 때 회원 잔액 업데이트시 에러 */
public class OrderMemberUpdateMoneyException extends DomainException {

  private static final String message = "존재하지 않는 회원입니다.";

  public OrderMemberUpdateMoneyException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
