package app.service.cart;

import app.entity.Cart;
import org.apache.ibatis.session.SqlSession;

public interface CartProductDeletePolicy {

  void deleteRequestQuantity(Cart cart, Long requestQuantity, SqlSession session) throws Exception;
}
