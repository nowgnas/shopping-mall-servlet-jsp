package app.service.cart;

import app.dao.CartDaoFrame;
import app.dao.DaoFrame;
import app.dao.member.MemberDaoFrame;
import app.dao.product.ProductDao;
import app.dto.cart.AllCartProductInfoDto;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;

import app.entity.Member;
import app.entity.Product;
import app.error.ErrorCode;
import app.error.exception.cart.OutOfStockException;
import app.error.exception.member.MemberNotFoundException;
import app.error.exception.product.ProductNotFoundException;
import app.utils.GetSessionFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartServiceImpl implements CartService {

  private DaoFrame<ProductAndMemberCompositeKey, Cart> cartDao;
  private DaoFrame<Long, Member> memberDao;
  private final ProductDao productDao = ProductDao.getInstance();

  public CartServiceImpl(CartDaoFrame<ProductAndMemberCompositeKey,Cart> cart, MemberDaoFrame<Long,Member> memberDao) {
    this.cartDao = cart;
    this.memberDao = memberDao;
  }

  @Override
  public AllCartProductInfoDto getCartProductListByMember(
      ProductAndMemberCompositeKey productAndMemberCompositeKeys) throws Exception {

    return null;

  }

  @Override
  public void putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Integer quantity) throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKey.getMemberId();
    Long productId = productAndMemberCompositeKey.getProductId();
    //MemberNotFoundException
    memberDao.selectById(memberId, session).orElseThrow(() -> new MemberNotFoundException(
        ErrorCode.MEMBER_NOT_FOUND));
    //ProductNotFoundException
    Product product = productDao.selectById(productId, session)
        .orElseThrow(() -> new ProductNotFoundException(ErrorCode.ITEM_NOT_FOUND));
    //OutOfStockException
    if (product.getQuantity() < quantity) {
      throw new OutOfStockException(ErrorCode.QUANTITY_IS_NOT_SUFFICIENT);
    }

    cartDao.insert(Cart.CartCompKeyBuilder(productAndMemberCompositeKey, quantity), session);
  }

  @Override
  public void updateQuantityOfCartProduct(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Long productId,
      Integer requestUpdateQuantity)
      throws Exception {
  }

  @Override
  public void delete(ProductAndMemberCompositeKey productAndMemberCompositeKey)
      throws Exception {


  }
}
