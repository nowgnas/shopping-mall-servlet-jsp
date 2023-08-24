package app.service.cart;

import app.dao.CartDaoFrame;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.exception.ErrorCode;
import app.exception.cart.OutOfStockException;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class UpdateCartServiceImpl implements UpdateCartService {

  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDao;
  private CartProductDeletePolicy cartProductDeletePolicy;

  @Override
  public void increaseQuantity(Cart cart, Long requestQuantity, Long stock, SqlSession session)
      throws Exception {
    // 상품의 재고가 부족한 경우
    Long totalRequestQuantity = requestQuantity + cart.getProductQuantity();
    if (totalRequestQuantity > stock) {
      throw new OutOfStockException(ErrorCode.QUANTITY_IS_NOT_SUFFICIENT);
    } else {
      cartDao.update(Cart.updateCart(cart, totalRequestQuantity), session);
    }
  }

  @Override
  public void decreaseQuantity(Cart cart, Long requestQuantity, SqlSession session)
      throws Exception {
    cartProductDeletePolicy.deleteRequestQuantity(cart, requestQuantity, session);
  }
}
