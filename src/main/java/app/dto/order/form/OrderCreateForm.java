package app.dto.order.form;

import app.dto.product.response.ProductDetailForOrder;
import app.dto.response.OrderMemberDetail;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

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
                productDetail.getQuantity(),
                productDetail.getUrl()))
        .defaultAddress(
            new AddressDto(
                orderMemberDetail.getAddress() == null
                    ? null
                    : orderMemberDetail.getAddress().getRoadName(),
                orderMemberDetail.getAddress() == null
                    ? null
                    : orderMemberDetail.getAddress().getAddrDetail(),
                orderMemberDetail.getAddress() == null
                    ? null
                    : orderMemberDetail.getAddress().getZipCode()))
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
  public static class AddressDto {
    private String roadName;
    private String addrDetail;
    private String zipCode;
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class CouponDto {
    private Long id;
    private String name;
    private String discountPolicy;
    private Integer discountValue;
    private String status;
  }

  @Getter
  @AllArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ProductDto {
    private Long id;
    private String name;
    private Long price;
    private Long quantity;
    private String thumbnailUrl;
  }
}
