package app.dao.cart;

import app.dto.cart.CartAndProductDto;
import app.entity.Cart;
import app.entity.ProductAndMemberCompositeKey;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class CartDao implements CartDaoFrame<ProductAndMemberCompositeKey, Cart> {

  @Override
  public int insert(Cart cart, SqlSession session) throws Exception {
    return session.insert("cart.insert", cart);
  }

  @Override
  public int update(Cart cart, SqlSession session) throws Exception {
    return session.update("cart.update", cart);
  }

  @Override
  public int deleteById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws Exception {
    return session.delete("cart.delete", productAndMemberCompositeKey);
  }

  @Override
  public Optional<Cart> selectById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws Exception {
    return Optional.ofNullable(
        session.selectOne("cart.select", productAndMemberCompositeKey));
  }

  @Override
  public List<Cart> selectAll(SqlSession session) throws Exception {
    return session.selectList("select-all");
  }

  @Override
  public List<Cart> getCartProductListByMember(Long memberId, SqlSession session) {
    return session.selectList("select-all-by-member", memberId);
  }

  @Override
  public List<CartAndProductDto> getAllCartsAndAllProductsByMember(Long memberId,
      SqlSession session) {
    return session.selectList("select-all-cart-and-product-by-member", memberId);
  }

  @Override
  public int bulkDelete(List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys,
      SqlSession session) {
    return session.delete("cart.bulkDelete", productAndMemberCompositeKeys);
  }
}
