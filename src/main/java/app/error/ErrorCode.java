package app.error;

import static javax.servlet.http.HttpServletResponse.*;

import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;

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
  CANNOT_DELETE_ORDER(SC_BAD_REQUEST, "ERR-ORDER-004", "해당 주문을 삭제할 수 없습니다."),
  CANNOT_INSERT_PRODUCT_ORDER(SC_BAD_REQUEST, "ERR-PRODUCT_ORDER-001", "해당 주문 상품을 생성할 수 없습니다."),
  CANNOT_INSERT_DELIVERY(SC_BAD_REQUEST, "ERR-DELIVERY-001", "해당 배송지를 생성할 수 없습니다."),
  CANNOT_INSERT_PAYMENT(SC_BAD_REQUEST, "ERR-PAYMENT-001", "해당 결제 정보를 생성할 수 없습니다."),
  EMAIL_IS_NOT_INVALID(HttpServletResponse.SC_BAD_REQUEST, "0000", "유효한 이메일이 아닙니다."),
  PASSWORD_IS_NOT_INVALID(
      HttpServletResponse.SC_BAD_REQUEST, "0000", "비밀번호는 영문, 숫자 형식으로 최소 8자 ~ 최대 16자 사이로 입력해야 합니다."),
  NAME_IS_NOT_INVALID(
      HttpServletResponse.SC_BAD_REQUEST, "0000", "이름은 최소 1자에서 최대 20자로 입력할 수 있습니다."),
  EMAIL_IS_NOT_DUPLICATE(HttpServletResponse.SC_BAD_REQUEST, "0000", "가입 된 이메일 입니다."),
  ITEM_NOT_FOUND(SC_BAD_REQUEST, "ERR-PRODUCT-001", "아이템이 존재하지 않습니다");

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
