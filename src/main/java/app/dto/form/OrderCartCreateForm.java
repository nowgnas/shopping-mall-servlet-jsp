package app.dto.form;

import app.enums.CouponPolicy;
import app.enums.CouponStatus;
import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCartCreateForm {

  private String memberName;
  private List<ProductDto> products;
  private AddressDto defaultAddress;
  private List<CouponDto> coupons;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class AddressDto {
    private Long addressId;
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
    private CouponPolicy discountPolicy;
    private Integer discountValue;
    private CouponStatus status;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ProductDto {
    private Long productId;
    private String name;
    private String imageUrl;
    private String price;
    private Long quantity;
  }
}
