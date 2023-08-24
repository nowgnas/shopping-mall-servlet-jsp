package app.dto.order.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
