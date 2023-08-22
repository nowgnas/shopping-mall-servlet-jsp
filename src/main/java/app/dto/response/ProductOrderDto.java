package app.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import app.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderDto {
  private Long orderId;
  private OrderStatus orderStatus;
  private LocalDateTime orderDate;
  private List<ProductDto> products;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class ProductDto {
    private Long productId;
    private String name;
    private String thumbnailUrl;
    private int price;
    private int quantity;
  }
}
