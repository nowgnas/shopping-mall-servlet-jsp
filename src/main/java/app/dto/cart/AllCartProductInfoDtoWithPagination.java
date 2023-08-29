package app.dto.cart;

import app.dto.paging.Pagination;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AllCartProductInfoDtoWithPagination {

  private AllCartProductInfoDto cartProductInfoDto;
  private Pagination paging;


  public static AllCartProductInfoDtoWithPagination getCartProductListWithPagination(
      AllCartProductInfoDto cartProductList, Pagination pagination) {
    return AllCartProductInfoDtoWithPagination.builder().cartProductInfoDto(cartProductList)
        .paging(pagination).build();
  }
}
