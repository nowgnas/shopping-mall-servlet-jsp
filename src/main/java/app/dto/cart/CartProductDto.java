package app.dto.cart;


import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class CartProductDto {

  private String productName;
  private Long productPrice;
  private Integer quantity;
  private String imgUrl;
  private Long price;

  private CartProductDto(){

  }

  public static List<CartProductDto> getProductInfo(List<ProductItemQuantity> productItemQuantity) {
    return productItemQuantity.stream().map(CartProductDto::getProductInfo)
        .collect(Collectors.toList());
  }

  private static CartProductDto getProductInfo(ProductItemQuantity productItemQuantity) {
    return CartProductDto.builder().quantity(productItemQuantity.getQuantity())
        .imgUrl(productItemQuantity.getUrl()).price(productItemQuantity.getPrice())
        .productName(productItemQuantity.getName()).build();
  }


}
