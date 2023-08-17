package app.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDto {
  private Long orderId;
  private String orderStatus;
  private LocalDateTime orderDate;
  private List<ProductDto> products;

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProductDto {
    private Long productId;
    private String name;
    private String thumbnailUrl;
    private int price;
    private int quantity;
  }
}
