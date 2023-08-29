package app.dao.cart;

import app.dao.DaoFrame;
import app.dto.cart.CartAndProductDto;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface CartDaoFrame<K, V> extends DaoFrame<K, V> {

  List<Cart> getCartProductListByMember(Long memberId, SqlSession session);

  List<CartAndProductDto> getAllCartsAndAllProductsByMember(Long memberId, SqlSession sqlSession);

  int bulkDelete(
      List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys, SqlSession session);
}
