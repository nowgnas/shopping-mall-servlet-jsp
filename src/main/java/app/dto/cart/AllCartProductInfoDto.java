package app.dto.cart;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class AllCartProductInfoDto {

  private List<CartProductDto> cartProductDtoList;
  private Long totalPrice;

  private AllCartProductInfoDto() {

  }

  public static AllCartProductInfoDto getCustomerViewOfCartInfo(
      List<CartProductDto> cartProductDtoList) {
    Long totalProductPriceInCart = 0L;
    for (CartProductDto cartProduct : cartProductDtoList) {
      totalProductPriceInCart += cartProduct.getPrice();
    }
    return AllCartProductInfoDto.builder().cartProductDtoList(cartProductDtoList)
        .totalPrice(totalProductPriceInCart).build();
  }
}
