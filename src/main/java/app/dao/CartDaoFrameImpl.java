package app.dao;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class CartDaoFrameImpl implements CartDaoFrame<ProductAndMemberCompositeKey, Cart> {

  @Override
  public int insert(Cart cart, SqlSession session) throws Exception {
    int returnValue = session.insert("cart.insert", cart);
    session.close();
    return returnValue;
  }

  @Override
  public int update(Cart cart, SqlSession session) throws Exception {

    int returnValue = session.update("cart.update", cart);
    session.close();
    return returnValue;
  }

  @Override
  public int deleteById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws Exception {
    int returnValue = session.delete("cart.delete", productAndMemberCompositeKey);
    session.close();
    return returnValue;
  }

  @Override
  public Optional<Cart> selectById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws Exception {
    Optional<Cart> cartOptional = Optional.ofNullable(
        session.selectOne("cart.select", productAndMemberCompositeKey));
    session.close();
    return cartOptional;
  }

  @Override
  public List<Cart> selectAll(SqlSession session) throws Exception {
    List<Cart> cartList = session.selectList("select-all");
    session.close();
    return cartList;
  }

  @Override
  public List<Cart> getCartProductListByMember(Long memberId, SqlSession session) {
    List<Cart> cartList = session.selectList("select-all-by-member");
    session.close();
    return cartList;
  }
}
