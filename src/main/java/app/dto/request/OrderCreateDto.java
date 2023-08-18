package app.dto.request;

import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateDto {

  private boolean isCart;
  private Long memberId;
  private Long couponId;
  private AddressDto address;
  private List<ProductDto> products;
  private Long totalPrice;

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
  public static class ProductDto {
    private Long productId;
    private Long quantity;
  }
}
