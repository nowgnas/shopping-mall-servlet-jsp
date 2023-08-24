package app.service.cart;

import app.dao.cart.CartDao;
import app.entity.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.exception.ErrorCode;
import app.exception.cart.CartQuantityIsUnder0Exception;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class ThrowExceptionUserRequestOverProductQuantityInCart implements
    CartProductDeletePolicy {

  private CartDao<ProductAndMemberCompositeKey, Cart> cartDao;


  @Override
  public void deleteRequestQuantity(Cart cart, Long requestQuantity, SqlSession session)
      throws Exception {

    Long totalRequestQuantity = cart.getProductQuantity() - requestQuantity;
    if (totalRequestQuantity <= 0) {
      throw new CartQuantityIsUnder0Exception(ErrorCode.CART_CAN_NOT_STORE_UNDER_0_VALUE);
    } else {
      cartDao.update(Cart.updateCart(cart,totalRequestQuantity),
          session);
    }


  }
}
