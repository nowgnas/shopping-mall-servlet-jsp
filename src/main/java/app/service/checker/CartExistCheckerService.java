package app.service.checker;

import app.dao.CartDaoFrame;
import app.dao.DaoFrame;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.entity.Product;
import app.error.ErrorCode;
import app.error.exception.cart.CartNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class CartExistCheckerService implements
    EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> {
  @Override
  public Cart isExisted(DaoFrame<ProductAndMemberCompositeKey, Cart> daoFrame,
      ProductAndMemberCompositeKey id, SqlSession session) throws Exception {
    return daoFrame.selectById(id,session).orElseThrow(()-> new CartNotFoundException(
        ErrorCode.CART_IS_NOT_EXISTED));
  }
}
