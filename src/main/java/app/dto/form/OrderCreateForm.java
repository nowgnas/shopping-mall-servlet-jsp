package app.dto.form;

import app.enums.CouponPolicy;
import app.enums.CouponStatus;
import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateForm {

  private String memberName;
  private ProductDto product;
  private AddressDto defaultAddress;
  private List<CouponDto> coupons;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class AddressDto {
    private String roadName;
    private String addrDetail;
    private String zipCode;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CouponDto {
    private Long couponId;
    private String name;
    private String discountPolicy;
    private String discountValue;
    private String status;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ProductDto {
    private Long productId;
    private String name;
    private String imageUrl;
    private Long price;
  }
}
