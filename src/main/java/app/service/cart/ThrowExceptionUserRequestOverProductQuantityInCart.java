package app.service.cart;

import app.dao.cart.CartDaoFrame;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.cart.CartQuantityIsUnder0Exception;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class ThrowExceptionUserRequestOverProductQuantityInCart implements CartProductDeletePolicy {

  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame;

  @Override
  public void deleteRequestQuantity(Cart cart, Long requestQuantity, SqlSession session)
      throws Exception {

    Long totalRequestQuantity = cart.getProductQuantity() - requestQuantity;
    if (totalRequestQuantity <= 0) {
      throw new CartQuantityIsUnder0Exception();
    } else {
      cartDaoFrame.update(Cart.updateCart(cart, totalRequestQuantity), session);
    }
  }
}
