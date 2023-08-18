package app.error;

import java.util.Arrays;
import java.util.function.Supplier;
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;


@Getter
public enum ErrorCode implements Supplier<X> {
  //@formatter:off
  //시스템 에러
  NO_AUTHORIZATION(HttpServletResponse.SC_UNAUTHORIZED, "0000", "권한 오류"),
  PAYMENT_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0001", "결제 시스템 오류"),
  ORDER_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0002", "주문 시스템 오류"),
  MEMBER_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0003", "회원 시스템 오류"),
  CART_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0004", "장바구니 시스템 오류"),
  LIKE_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0005", "찜 시스템 오류"),
  DELIVERY_SYSTEM_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "0006", "배송 시스템 오류"),

  //회원
/*
1. regex 유효성 검사 email, password
2. 이미 존재하는 이메일
3. 일치하지 않는 회원 정보입니다.
 */

//  INVALID_EMAIL_SIGN_UP_DATA(),
//  INVALID_PASSWORD_SIGN_UP_PASSWORD_DATA(),
//  DUPLICATE_EMAIL(),
//  LOGIN_INFO_IS_NOT_MATCHED(),
  //장바구니
/*
1. 재고가 없는 상품입니다.
2. 존재하지 않는 상품입니다.
3. 이미 담겨져 있는 상품입니다.

 */

  CART_PRODUCT_IS_NOT_EXISTED(HttpServletResponse.SC_NOT_FOUND, "4001", "장바구니에 상품이 존재하지 않습니다."),
  CART_PRODUCT_IS_ALREADY_EXISTED(HttpServletResponse.SC_NOT_ACCEPTABLE, "4002", "장바구니에 해당 상품이 이미 존재합니다."),

  //주문
  QUANTITY_IS_NOT_SUFFICIENT(400, "3001", "재고가 없습니다."),
  CUSTOMER_IS_NOT_AFFORDABLE(402, "3002", "고객님의 잔액을 확인해주세요"),
  NO_PRODUCT_IN_CART(400, "3003", "상품이 카트에 존재하지 않습니다."),
  ADDRESS_IS_EMPTY(400, "3004", "고객님의 주소를 선택해주세요");

  //찜
/*
1. 존재하지 않은 상품입니다.
 */


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
  public
}