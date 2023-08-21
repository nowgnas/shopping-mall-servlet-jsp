package app.error;

import java.util.Arrays;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;


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