package app.service.cart;

import app.dao.cart.CartDaoFrame;
import app.dao.DaoFrame;
import app.dao.product.ProductDao;
import app.dto.cart.AllCartProductInfoDto;
import app.dto.cart.ProductInCartDto;
import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.member.MemberNotFoundException;
import app.service.checker.EntityExistCheckerService;
import app.service.product.StockCheckerService;
import app.utils.GetSessionFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CartServiceImpl implements CartService {

  private final ProductDao productDao = ProductDao.getInstance();
  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame;
  private DaoFrame<Long, Member> memberDao;
  private EntityExistCheckerService<Long, Member> memberExistCheckerService;
  private EntityExistCheckerService<Long, Product> productExistCheckerService;
  private EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> cartExistCheckerService;
  private StockCheckerService stockCheckerService;
  private UpdateCartService updateCartService;


  @Override
  public AllCartProductInfoDto getCartProductListByMember(
      Long memberId) throws Exception, MemberNotFoundException {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    //MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session).getId();
    List<Cart> cartList = cartDaoFrame.getCartProductListByMember(memberId, session);
    List<Long> productIdList = cartList.stream().map(p -> p.getProductId())
        .collect(Collectors.toList());
    List<ProductItemQuantity> allProductInfo = productDao.selectProductQuantity(productIdList,
        session);
    return AllCartProductInfoDto.getCustomerViewOfCartInfo(
        ProductInCartDto.getProductInfo(allProductInfo));
  }

  @Override
  public void putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Long requestQuantity) throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKey.getMemberId();
    Long productId = productAndMemberCompositeKey.getProductId();
    //MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session);
    //ProductNotFoundException
    Product product = productExistCheckerService.isExisted(productDao, productId, session);

    Optional<Cart> cartOptional = cartDaoFrame.selectById(productAndMemberCompositeKey, session);
    if (cartOptional.isPresent()) {
      updateCartService.increaseQuantity(cartOptional.get(), requestQuantity, product.getQuantity(),
          session);
    } else {
      cartDaoFrame.insert(Cart.cartCompKeyBuilder(productAndMemberCompositeKey, requestQuantity),
          session);
    }
  }

  @Override
  public void updateQuantityOfCartProduct(
      ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Long requestUpdateQuantity)
      throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKey.getMemberId();
    Long productId = productAndMemberCompositeKey.getProductId();
    //MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session);
    //ProductNotFoundException
    Product product = productExistCheckerService.isExisted(productDao, productId, session);
    //CartNotFoundException
    Cart cart = cartExistCheckerService.isExisted(cartDaoFrame, productAndMemberCompositeKey, session);
    if (requestUpdateQuantity > 0) {
      updateCartService.increaseQuantity(cart, requestUpdateQuantity, product.getQuantity(),
          session);
    } else {
      updateCartService.decreaseQuantity(cart, requestUpdateQuantity, session);
    }

  }

  @Override
  public void delete(ProductAndMemberCompositeKey productAndMemberCompositeKey, Long quantity)
      throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKey.getMemberId();
    Long productId = productAndMemberCompositeKey.getProductId();
    //MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session);
    //ProductNotFoundException
    productExistCheckerService.isExisted(productDao, productId, session);
    //CartNotFoundException
    cartExistCheckerService.isExisted(cartDaoFrame, productAndMemberCompositeKey, session);

    cartDaoFrame.deleteById(productAndMemberCompositeKey, session);

  }
}
