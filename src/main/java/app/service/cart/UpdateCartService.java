package app.service.cart;

import app.entity.Cart;
import org.apache.ibatis.session.SqlSession;

public interface UpdateCartService {

  void update(Cart cart, Long requestQuantity, Long stock, SqlSession session)
      throws Exception;
}
