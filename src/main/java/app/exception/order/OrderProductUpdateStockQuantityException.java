package app.exception.order;

import app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

/* 주문할 때 상품 수량 업데이트시 에러 */
public class OrderProductUpdateStockQuantityException extends DomainException {

  private static final String message = "존재하지 않는 상품입니다.";

  public OrderProductUpdateStockQuantityException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
