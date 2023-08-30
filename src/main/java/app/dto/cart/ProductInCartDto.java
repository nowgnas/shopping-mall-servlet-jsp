package app.dto.cart;

import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import java.util.Map;
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

  private Long memberId;
  private Long productId;
  private String productName;
  private Long productPrice;
  private Integer stock;
  private Integer productInCart;//상품재고
  private String imgUrl;
  private Long price;

public static List<ProductInCartDto> getProductInfo(
        List<ProductItemQuantity> productItemQuantityList, List<Cart> cartList) {

    Map<Long, Long> productIdToQuantityMap = cartList.stream()
        .collect(Collectors.toMap(Cart::getProductId, Cart::getProductQuantity));

    return productItemQuantityList.stream()
        .map(productItemQuantity -> {
            Long productId = productItemQuantity.getId();
            Integer quantityInCart = Math.toIntExact(
                productIdToQuantityMap.getOrDefault(productId, 0L));

            Cart cartItem = cartList.stream()
                .filter(cart -> cart.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

            Long memberId = (cartItem != null) ? cartItem.getMemberId() : null;

            return getProductInfo(productItemQuantity, quantityInCart, memberId);
        })
        .collect(Collectors.toList());
}

    private static ProductInCartDto getProductInfo(
            ProductItemQuantity productItemQuantity, Integer quantityInCart, Long memberId) {
        return ProductInCartDto.builder()
            .productId(productItemQuantity.getId())
            .stock(productItemQuantity.getQuantity())
            .memberId(memberId)
            .productInCart(quantityInCart)
            .productPrice(productItemQuantity.getPrice())
            .imgUrl(productItemQuantity.getUrl())
            .price(productItemQuantity.getPrice())
            .productName(productItemQuantity.getName())
            .build();
    }
}
