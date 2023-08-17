package app.error;

import java.util.Arrays;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.*;

@Getter
public enum ErrorCode {
  INTERNAL_SERVER_ERROR(SC_INTERNAL_SERVER_ERROR, "ERR-SERVER-001", "시스템 오류"),
  QUANTITY_IS_NOT_SUFFICIENT(400, "0001", null),
  CUSTOMER_IS_NOT_AFFORDABLE(400, "0002", null),
  NO_PRODUCT_IN_CART(400, "0003", null),
  ADDRESS_IS_UNAVAILABLE(400, "0004", null),
  CANNOT_FIND_ORDER(SC_BAD_REQUEST, "ERR-ORDER-001", "해당 주문을 찾을 수 없습니다."),
  CANNOT_INSERT_ORDER(SC_BAD_REQUEST, "ERR-ORDER-002", "해당 주문을 생성할 수 없습니다."),
  CANNOT_UPDATE_ORDER(SC_BAD_REQUEST, "ERR-ORDER-003", "해당 주문을 수정할 수 없습니다."),
  CANNOT_DELETE_ORDER(SC_BAD_REQUEST, "ERR-ORDER-004", "해당 주문을 삭제할 수 없습니다.");

  private final int status;
  private final String code;
  private final String message;
  // @formatter:on
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
