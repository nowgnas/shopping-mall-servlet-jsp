package app.service.cart;

import app.dao.CartDaoFrame;
import app.dao.product.ProductDao;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class DeleteCartWhenRestOfQuantityUnder0 implements CartProductDeletePolicy {

  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame;

  @Override
  public void deleteRequestQuantity(Cart cart, Long requestQuantity, SqlSession session)
      throws Exception {
    Long totalRequestQuantity = cart.getProductQuantity() - requestQuantity;
    if (totalRequestQuantity <= 0) {
      cartDaoFrame.deleteById(
          new ProductAndMemberCompositeKey(cart.getProductId(), cart.getMemberId()), session);
    } else {
      cartDaoFrame.update(Cart.updateCart(cart,totalRequestQuantity),
          session);
    }
  }
}
