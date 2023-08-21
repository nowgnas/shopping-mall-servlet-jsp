package app.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderDetailDto {
  private Long orderId;
  private String orderStatus;
  private LocalDateTime orderDate;
  private List<ProductDto> products;
  private String memberName;
  private DeliveryDto address;
  private PaymentDto payment;
  private CouponDto coupon;
  private int totalPrice;
  private int discountPrice;

  public int getDiscountPrice() {
    return totalPrice - payment.getActualAmount();
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ProductDto {
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private int price;
    private int quantity;
  }

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
  @NoArgsConstructor
  public static class PaymentDto {
    private String paymentType;
    private int actualAmount;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class CouponDto {
    private String couponName;
    private String couponStatus;
  }
}
