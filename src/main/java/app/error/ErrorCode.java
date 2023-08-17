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
    EMAIL_IS_NOT_INVALID(HttpServletResponse.SC_BAD_REQUEST, "0000", "유효한 이메일이 아닙니다."),
    PASSWORD_IS_NOT_INVALID(HttpServletResponse.SC_BAD_REQUEST, "0000", "비밀번호는 영문, 숫자 형식으로 최소 8자 ~ 최대 16자 사이로 입력해야 합니다."),
    NAME_IS_NOT_INVALID(HttpServletResponse.SC_BAD_REQUEST, "0000", "이름은 최소 1자에서 최대 20자로 입력할 수 있습니다."),
    EMAIL_IS_NOT_DUPLICATE(HttpServletResponse.SC_BAD_REQUEST, "0000", "가입 된 이메일 입니다."),
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0000", "서버에러");

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