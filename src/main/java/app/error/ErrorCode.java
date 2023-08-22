package app.error;

import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import static javax.servlet.http.HttpServletResponse.*;

@Getter
public enum ErrorCode{
  //@formatter:off
  //시스템 에러
  NO_AUTHORIZATION(HttpServletResponse.SC_UNAUTHORIZED, "0000", "권한 오류"),
  PAYMENT_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0001", "결제 시스템 오류"),
  ORDER_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0002", "주문 시스템 오류"),
  MEMBER_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0003", "회원 시스템 오류"),
  CART_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0004", "장바구니 시스템 오류"),
  LIKE_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0005", "찜 시스템 오류"),
  DELIVERY_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0006", "배송 시스템 오류"),


  CART_PRODUCT_IS_NOT_EXISTED(HttpServletResponse.SC_NOT_FOUND, "4001", "장바구니에 상품이 존재하지 않습니다."),
  CART_PRODUCT_IS_ALREADY_EXISTED(HttpServletResponse.SC_NOT_ACCEPTABLE, "4002", "장바구니에 해당 상품이 이미 존재합니다."),
  CART_PRODUCT_IS_OUT_OF_STOCK(HttpServletResponse.SC_BAD_REQUEST,"4003","재고가 존재하지 않습니다."),
  CART_CAN_NOT_STORE_UNDER_0_VALUE(HttpServletResponse.SC_NOT_ACCEPTABLE, "4004", "0개 이하의 상품을 담을 수 없습니다."),
  CART_CAN_NOT_STORE_MORE_THAN_QUANTITY(HttpServletResponse.SC_NOT_ACCEPTABLE, "4005", "재고 보다 많은 상품을 담을 수 없습니다."),


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
  CANNOT_UPDATE_DELIVERY(SC_BAD_REQUEST, "ERR-DELIVERY-002", "해당 배송지를 수정할 수 없습니다."),
  CANNOT_FIND_DELIVERY(SC_BAD_REQUEST, "ERR-DELIVERY-003", "해당 배송지를 찾을 수 없습니다."),
  CANNOT_UPDATE_COUPON(SC_BAD_REQUEST, "ERR-COUPON-001", "해당 쿠폰을 수정할 수 없습니다."),
  CANNOT_FIND_COUPON(SC_BAD_REQUEST, "ERR-COUPON-002", "해당 쿠폰을 찾을 수 없습니다."),
  CANNOT_INSERT_PAYMENT(SC_BAD_REQUEST, "ERR-PAYMENT-001", "해당 결제 정보를 생성할 수 없습니다."),
  MEMBER_NOT_FOUND(SC_NOT_FOUND, "0000", "해당 아이디의 회원은 존재 하지 않습니다."),
  CANNOT_FIND_PAYMENT(SC_BAD_REQUEST, "ERR-PAYMENT-002", "해당 결제 정보를 찾을 수 없습니다."),
  REGISTER_FAIL(SC_BAD_REQUEST, "0000", "회원 가입에 실패 했습니다"),
  EMAIL_IS_NOT_DUPLICATE(HttpServletResponse.SC_BAD_REQUEST, "0000", "가입 된 이메일 입니다."),
  ITEM_NOT_FOUND(SC_BAD_REQUEST, "ERR-PRODUCT-001", "아이템이 존재하지 않습니다"),
  LOGIN_FAIL(HttpServletResponse.SC_BAD_REQUEST, "0000", "아이디나 비밀번호가 일치하지 않습니다."),
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

