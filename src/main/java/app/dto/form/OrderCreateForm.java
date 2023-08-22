package app.dto.form;

import app.dto.product.response.ProductDetailForOrder;
import app.dto.response.OrderMemberDetail;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class OrderCreateForm {

  private String memberName;
  private ProductDto product;
  private AddressDto defaultAddress;
  private List<CouponDto> coupons;

  public static OrderCreateForm of(
      OrderMemberDetail orderMemberDetail, ProductDetailForOrder productDetail) {
    return OrderCreateForm.builder()
        .memberName(orderMemberDetail.getName())
        .product(
            new ProductDto(
                productDetail.getId(),
                productDetail.getName(),
                productDetail.getPrice(),
                productDetail.getUrl()))
        .defaultAddress(
            new AddressDto(
                orderMemberDetail.getAddress().getRoadName(),
                orderMemberDetail.getAddress().getAddrDetail(),
                orderMemberDetail.getAddress().getZipCode()))
        .coupons(
            orderMemberDetail.getCoupons().stream()
                .map(
                    c ->
                        new CouponDto(
                            c.getId(),
                            c.getName(),
                            c.getDiscountPolicy(),
                            c.getDiscountValue(),
                            c.getStatus()))
                .collect(Collectors.toList()))
        .build();
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  private static class AddressDto {
    private String roadName;
    private String addrDetail;
    private String zipCode;
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  private static class CouponDto {
    private Long id;
    private String name;
    private String discountPolicy;
    private String discountValue;
    private String status;
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  private static class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private String thumbnailUrl;
  }
}
