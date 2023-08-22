package app.service.cart;

import app.dto.cart.AllCartProductInfoDto;
import app.dto.comp.ProductAndMemberCompositeKey;

public interface CartService {

  AllCartProductInfoDto getCartProductListByMember(ProductAndMemberCompositeKey productAndMemberCompositeKeys)
      throws Exception;

  void putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Integer quantity)
      throws Exception;

  void updateQuantityOfCartProduct(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Long productId, Integer updateQuantity) throws Exception;

  void delete(ProductAndMemberCompositeKey productAndMemberCompositeKeyList) throws Exception;


}
