package app.dto.cart;

import app.dto.product.ProductItemQuantity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ProductInCartDto {

  private String productName;
  private Long productPrice;
  private Integer quantity;
  private String imgUrl;
  private Long price;

  private ProductInCartDto() {}

  public static List<ProductInCartDto> getProductInfo(
      List<ProductItemQuantity> productItemQuantity) {
    return productItemQuantity.stream()
        .map(ProductInCartDto::getProductInfo)
        .collect(Collectors.toList());
  }

  private static ProductInCartDto getProductInfo(ProductItemQuantity productItemQuantity) {
    return ProductInCartDto.builder()
        .quantity(productItemQuantity.getQuantity())
        .imgUrl(productItemQuantity.getUrl())
        .price(productItemQuantity.getPrice())
        .productName(productItemQuantity.getName())
        .build();
  }
}
