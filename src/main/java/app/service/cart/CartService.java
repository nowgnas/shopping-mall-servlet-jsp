package app.service.cart;

import app.dto.cart.AllCartProductInfoDto;
import app.dto.cart.AllCartProductInfoDtoWithPagination;
import app.dto.paging.Pagination;
import app.entity.ProductAndMemberCompositeKey;

public interface CartService {

  AllCartProductInfoDtoWithPagination getCartProductListByMemberPagination(Long memberId) throws Exception;
  AllCartProductInfoDto getCartProductListByMember(Long memberId) throws Exception;

  void putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey, Long quantity)
      throws Exception;

  void updateQuantityOfCartProduct(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Long updateQuantity)
      throws Exception;

  void delete(ProductAndMemberCompositeKey productAndMemberCompositeKeyList)
      throws Exception;
}
