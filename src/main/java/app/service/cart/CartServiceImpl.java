package app.service.cart;

import app.dao.CartDaoFrame;
import app.dao.CartDaoFrameImpl;
import app.dao.product.ProductDao;
import app.dto.cart.AllCartProductInfoDto;
import app.dto.cart.CartProductDto;
import app.dto.cart.CartProductQuantityInfo;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import app.entity.Product;
import app.error.ErrorCode;
import app.utils.GetSessionFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartServiceImpl implements CartService {

  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame;
  private ProductDao productDao;

  public CartServiceImpl(CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDao,
      ProductDao productDao) {
    this.cartDaoFrame = new CartDaoFrameImpl();
    this.productDao = ProductDao.getInstance();
  }

  @Override
  public AllCartProductInfoDto getCartProductListByMember(
      ProductAndMemberCompositeKey productAndMemberCompositeKeys) throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKeys.getMemberId();
    List<Cart> cartsByMember = cartDaoFrame.getCartProductListByMember(memberId, session);
    List<Long> productIdList = cartsByMember.stream().map(Cart::getProductId)
        .collect(Collectors.toList());
    List<ProductItemQuantity> productItemQuantity = productDao.selectProductQuantity(productIdList, session);

    return AllCartProductInfoDto.getCustomerViewOfCartInfo(CartProductDto.getProductInfo(productItemQuantity));

  }

  @Override
  public ErrorCode putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Integer quantity) throws Exception {
    int count = cartDaoFrame.insert(Cart.CartCompKeyBuilder(productAndMemberCompositeKey, quantity),
        GetSessionFactory.getInstance().openSession());
        return count > 0 ? ErrorCode.valueOfCode("4002") : null;
  }

  @Override
  public ErrorCode updateQuantityOfCartProduct(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Long productId,
      Integer requestUpdateQuantity)
      throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    return null;
  }

  @Override
  public ErrorCode delete(ProductAndMemberCompositeKey productAndMemberCompositeKey)
      throws Exception {

    int count = cartDaoFrame.deleteById(productAndMemberCompositeKey,
        GetSessionFactory.getInstance()
            .openSession());

    return count > 0 ? ErrorCode.valueOfCode("4001") : null;

  }
}
