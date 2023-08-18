package app.service.cart;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.error.ErrorCode;
import java.util.List;

public interface CartService {

  List<Cart> getCartProductListByMember(List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys)
      throws Exception;

  ErrorCode putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Integer quantity)
      throws Exception;

  ErrorCode updateQuantityOfCartProduct(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Integer updateQuantity) throws Exception;

  ErrorCode delete(ProductAndMemberCompositeKey productAndMemberCompositeKeyList) throws Exception;


}
