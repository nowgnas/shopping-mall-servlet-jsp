package app.dto.cart;

import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProductQuantityInfo {

  private Long productId;
  private Integer stock;

  public static CartProductQuantityInfo getQuantityInfoFromProduct(
      ProductItemQuantity productItemQuantity) {
    return new CartProductQuantityInfo(productItemQuantity.getId(),
        productItemQuantity.getQuantity());
  }
}
