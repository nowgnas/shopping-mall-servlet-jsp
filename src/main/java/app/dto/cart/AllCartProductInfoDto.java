package app.dto.cart;

import app.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class AllCartProductInfoDto {

  private List<ProductInCartDto> cartProductDtoList;
  private Long totalPrice;

  private AllCartProductInfoDto() {}

  public static AllCartProductInfoDto getCustomerViewOfCartInfo(
      List<ProductInCartDto> cartProductDtoList) {
    Long totalProductPriceInCart = 0L;
    for (ProductInCartDto cartProduct : cartProductDtoList) {
      totalProductPriceInCart += (long)(cartProduct.getProductPrice().intValue() * cartProduct.getProductInCart().intValue());
    }
    return AllCartProductInfoDto.builder()
        .cartProductDtoList(cartProductDtoList)
        .totalPrice(totalProductPriceInCart)
        .build();
  }
}
