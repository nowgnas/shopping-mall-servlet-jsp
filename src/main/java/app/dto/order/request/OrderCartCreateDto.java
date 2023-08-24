package app.dto.order.request;

import app.dto.cart.CartAndProductDto;
import app.entity.Delivery;
import app.entity.Order;
import app.entity.Payment;
import app.entity.ProductOrder;
import app.enums.DeliveryStatus;
import app.enums.OrderStatus;
import app.enums.PaymentType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCartCreateDto {

  private Long memberId;
  private Long couponId;
  private String roadName;
  private String addrDetail;
  private String zipCode;
  private List<ProductDto> products;
  private Long totalPrice;

  public void setProducts(List<CartAndProductDto> cartAndProductDtos) {
    products =
        cartAndProductDtos.stream()
            .map(
                cp -> new ProductDto(cp.getProductId(), cp.getPrice(), cp.getCartProductQuantity()))
            .collect(Collectors.toList());
  }

  public Order toOrderEntity() {
    return Order.builder()
        .memberId(memberId)
        .couponId(couponId)
        .status(OrderStatus.PENDING.name())
        .build();
  }

  public List<ProductOrder> toProductOrderEntities(Long orderId) {
    return products.stream()
        .map(
            p ->
                ProductOrder.builder()
                    .orderId(orderId)
                    .productId(p.productId)
                    .price(p.price)
                    .quantity(p.quantity)
                    .build())
        .collect(Collectors.toList());
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

  @Getter
  @AllArgsConstructor
  public static class ProductDto {
    private Long productId;
    private Long price;
    private Long quantity;
  }
}
