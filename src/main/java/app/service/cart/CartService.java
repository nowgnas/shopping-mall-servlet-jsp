package app.service.cart;

import app.dto.cart.AllCartProductInfoDto;
import app.entity.ProductAndMemberCompositeKey;

public interface CartService {

  AllCartProductInfoDto getCartProductListByMember(Long memberId)
      throws Exception;

  void putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Long quantity)
      throws Exception;

  void updateQuantityOfCartProduct(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Long updateQuantity) throws Exception;

  void delete(ProductAndMemberCompositeKey productAndMemberCompositeKeyList, Long quantity)
      throws Exception;


}
