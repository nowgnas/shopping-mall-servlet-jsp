package app.error;

import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;


@Getter
public enum ErrorCode {
  //@formatter:off
 QUANTITY_IS_NOT_SUFFICIENT(400,"0001",null),
 CUSTOMER_IS_NOT_AFFORDABLE(400,"0002",null),
 NO_PRODUCT_IN_CART(400,"0003",null),
 ADDRESS_IS_UNAVAILABLE(400,"0004",null),
 PRODUCT_IS_NOT_VALID(HttpServletResponse.SC_BAD_REQUEST, "0000", null);

  private final int status;
  private final String code;
  private final String message;
  //@formatter:on
  ErrorCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public static ErrorCode valueOfCode(String errorCode) {
    return Arrays.stream(values())
        .filter(value -> value.code.equals(errorCode))
        .findAny()
        .orElse(null);
  }
}