package app.dto.form;

import java.util.List;
import java.util.stream.Collectors;

import app.dto.cart.CartAndProductDto;
import app.dto.product.response.ProductDetailForOrder;
import app.dto.response.OrderMemberDetail;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class OrderCartCreateForm {

  private String memberName;
  private List<ProductDto> products;
  private AddressDto defaultAddress;
  private List<CouponDto> coupons;

  public static OrderCartCreateForm of(
      OrderMemberDetail orderMemberDetail, List<CartAndProductDto> cartAndProductDtos) {
    return OrderCartCreateForm.builder()
        .memberName(orderMemberDetail.getName())
        .products(
            cartAndProductDtos.stream()
                .map(cp -> new ProductDto(cp.getProductId(), cp.getName(), cp.getPrice(), cp.getCartProductQuantity()))
                .collect(Collectors.toList()))
        .defaultAddress(
            new OrderCartCreateForm.AddressDto(
                orderMemberDetail.getAddress().getRoadName(),
                orderMemberDetail.getAddress().getAddrDetail(),
                orderMemberDetail.getAddress().getZipCode()))
        .coupons(
            orderMemberDetail.getCoupons().stream()
                .map(
                    c ->
                        new OrderCartCreateForm.CouponDto(
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
  }
}
