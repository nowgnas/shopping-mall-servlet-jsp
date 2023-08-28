package app.service.cart;

import app.dao.cart.CartDaoFrame;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.ErrorCode;
import app.exception.cart.OutOfStockException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class UpdateCartServiceImpl implements UpdateCartService {

  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDao;
  private CartProductDeletePolicy cartProductDeletePolicy;


  @Override
  public void update(Cart cart, Long requestQuantity, Long stock, SqlSession session)
      throws Exception {
    if (requestQuantity.intValue() <= 0) {
      cartProductDeletePolicy.deleteRequestQuantity(cart, requestQuantity, session);
    } else if (stock < requestQuantity) {
      throw new OutOfStockException(ErrorCode.CART_PRODUCT_IS_OUT_OF_STOCK);
    } else {
      cartDao.update(Cart.updateCart(cart, requestQuantity), session);
    }
  }
}
