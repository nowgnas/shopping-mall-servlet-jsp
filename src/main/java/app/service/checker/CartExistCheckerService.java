package app.service.checker;

import app.dao.cart.DaoFrame;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class CartExistCheckerService implements
    EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> {

  @Override
  public Cart isExisted(DaoFrame<ProductAndMemberCompositeKey, Cart> daoFrame,
      ProductAndMemberCompositeKey id, SqlSession session) throws Exception {
    return daoFrame.selectById(id, session)
        .orElseThrow(() -> new app.exception.cart.CartNotFoundException(
            ErrorCode.CART_IS_NOT_EXISTED));
  }
}
