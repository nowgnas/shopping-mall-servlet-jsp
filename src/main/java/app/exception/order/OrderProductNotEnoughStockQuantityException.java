package app.exception.order;

import app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

/* 주문할 때 상품 수량 재고 부족 에러 */
public class OrderProductNotEnoughStockQuantityException extends DomainException {

  private static final String message = "상품의 재고가 부족합니다.";

  public OrderProductNotEnoughStockQuantityException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
