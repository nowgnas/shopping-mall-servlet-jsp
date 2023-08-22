package app.dao;

import app.dto.cart.CartAndProductDto;
import app.entity.Cart;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface CartDaoFrame<K,V> extends DaoFrame<K,V> {
   List<Cart> getCartProductListByMember(Long memberId, SqlSession session);
   List<CartAndProductDto>getAllCartsAndAllProductsByMember(Long memberId, SqlSession sqlSession);

}
