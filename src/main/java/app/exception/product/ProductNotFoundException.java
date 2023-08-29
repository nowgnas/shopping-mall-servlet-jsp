package app.exception.product;

import app.exception.DomainException;
import javax.servlet.http.HttpServletResponse;

public class ProductNotFoundException extends DomainException {
  private static final String message = "상품이 존재하지 않습니다";

  public ProductNotFoundException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_NOT_FOUND;
  }
}
