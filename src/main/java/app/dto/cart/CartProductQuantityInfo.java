package app.dto.cart;

import app.dto.product.ProductItemQuantity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductQuantityInfo {

  private Long productId;
  private Integer stock;

  public static CartProductQuantityInfo getQuantityInfoFromProduct(
      ProductItemQuantity productItemQuantity) {
    return new CartProductQuantityInfo(
        productItemQuantity.getId(), productItemQuantity.getQuantity());
  }
}
