package app.service.cart;

import app.dao.CartDaoFrame;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.error.ErrorCode;
import app.error.exception.cart.CartQuantityIsUnder0Exception;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class ThrowExceptionUserRequestOverProductQuantityInCart implements
    CartProductDeletePolicy {

  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame;


  @Override
  public void deleteRequestQuantity(Cart cart, Long requestQuantity, SqlSession session)
      throws Exception {

    Long totalRequestQuantity = cart.getProductQuantity() - requestQuantity;
    if (totalRequestQuantity <= 0) {
      throw new CartQuantityIsUnder0Exception(ErrorCode.CART_CAN_NOT_STORE_UNDER_0_VALUE);
    } else {
      cartDaoFrame.update(Cart.updateCart(cart,totalRequestQuantity),
          session);
    }


  }
}
