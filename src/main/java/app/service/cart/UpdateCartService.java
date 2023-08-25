package app.service.cart;

import app.entity.Cart;
import org.apache.ibatis.session.SqlSession;

public interface UpdateCartService {

  void increaseQuantity(Cart cart, Long requestQuantity, Long stock, SqlSession session)
      throws Exception;

  void decreaseQuantity(Cart cart, Long requestQuantity, SqlSession session) throws Exception;
}
