package app.dto.request;

import java.util.List;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCancelDto {

  private Long orderId;
  private Long memberId;
  private Long couponId;
  private DeliveryDto delivery;
  private List<ProductDto> products;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class DeliveryDto {
    private String roadName;
    private String addrDetail;
    private String zipCode;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class PaymentDto {
    private Long paymentId;
    private Long actualAmount;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ProductDto {
    private Long productId;
    private Long quantity;
  }
}
