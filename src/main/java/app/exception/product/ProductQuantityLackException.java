package app.exception.product;

import app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

public class ProductQuantityLackException extends DomainException {
  private static final String message = "상품 수랑이 부족합니다";

  public ProductQuantityLackException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
