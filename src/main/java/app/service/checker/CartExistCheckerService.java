package app.service.checker;

import app.dao.DaoFrame;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.cart.CartNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@AllArgsConstructor
public class CartExistCheckerService
    implements EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> {

  @Override
  public Cart isExisted(
      DaoFrame<ProductAndMemberCompositeKey, Cart> daoFrame,
      ProductAndMemberCompositeKey id,
      SqlSession session)
      throws Exception {
    return daoFrame
        .selectById(id, session)
        .orElseThrow(
            CartNotFoundException::new);
  }
}
