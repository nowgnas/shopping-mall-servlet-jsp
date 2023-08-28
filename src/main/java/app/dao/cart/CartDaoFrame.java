package app.dao.cart;

import app.dao.DaoFrame;
import app.dto.cart.CartAndProductDto;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface CartDaoFrame<K, V> extends DaoFrame<K, V> {

  Long getTheNumberOfTotalProductInCart(Long memberId, SqlSession session);

  List<Cart> getCartProductListByMember(Long memberId, SqlSession session);

  List<CartAndProductDto> getAllCartsAndAllProductsByMember(Long memberId, SqlSession sqlSession);

  int bulkDelete(List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys,
      SqlSession session);
}
