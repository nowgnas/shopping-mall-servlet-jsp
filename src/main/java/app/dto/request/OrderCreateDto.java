package app.dto.request;

import app.entity.Delivery;
import app.entity.Order;
import app.entity.Payment;
import app.entity.ProductOrder;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import app.enums.PaymentType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateDto {

  private Long memberId;
  private Long couponId;
  private String roadName;
  private String addrDetail;
  private String zipCode;
  private Long productId;
  private Long price;
  private Long quantity;
  private Long totalPrice;

  public Order toOrderEntity() {
    return Order.builder()
        .memberId(memberId)
        .couponId(couponId)
        .status(OrderStatus.PENDING.name())
        .build();
  }

  public ProductOrder toProductOrderEntity(Long orderId) {
    return ProductOrder.builder()
        .orderId(orderId)
        .productId(productId)
        .price(price)
        .quantity(quantity)
        .build();
  }

  public Delivery toDeliveryEntity(Long orderId) {
    return Delivery.builder()
        .orderId(orderId)
        .roadName(roadName)
        .addrDetail(addrDetail)
        .zipCode(zipCode)
        .status(DeliveryStatus.PENDING.name())
        .build();
  }

  public Payment toPaymentEntity(Long orderId) {
    return Payment.builder()
        .orderId(orderId)
        .type(PaymentType.CASH.name())
        .actualAmount(totalPrice)
        .build();
  }
}
