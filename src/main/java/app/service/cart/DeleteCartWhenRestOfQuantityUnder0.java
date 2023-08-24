package app.service.cart;

import app.dao.cart.CartDao;
import app.entity.ProductAndMemberCompositeKey;
import app.entity.Cart;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class DeleteCartWhenRestOfQuantityUnder0 implements CartProductDeletePolicy {

  private CartDao<ProductAndMemberCompositeKey, Cart> cartDao;

  @Override
  public void deleteRequestQuantity(Cart cart, Long requestQuantity, SqlSession session)
      throws Exception {
    Long totalRequestQuantity = cart.getProductQuantity() - requestQuantity;
    if (totalRequestQuantity <= 0) {
      cartDao.deleteById(
          new ProductAndMemberCompositeKey(cart.getProductId(), cart.getMemberId()), session);
    } else {
      cartDao.update(Cart.updateCart(cart,totalRequestQuantity),
          session);
    }
  }
}
