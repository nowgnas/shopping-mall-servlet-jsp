package app.service.cart;

import app.dao.DaoFrame;
import app.dao.cart.CartDaoFrame;
import app.dao.product.ProductDao;
import app.dto.cart.AllCartProductInfoDto;
import app.dto.cart.AllCartProductInfoDtoWithPagination;
import app.dto.cart.ProductInCartDto;
import app.dto.paging.Pagination;
import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.entity.ProductAndMemberCompositeKey;
import app.service.checker.EntityExistCheckerService;
import app.service.product.StockCheckerService;
import app.utils.GetSessionFactory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CartServiceImpl implements CartService {

  private final ProductDao productDao = ProductDao.getInstance();
  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDao;
  private DaoFrame<Long, Member> memberDao;
  private EntityExistCheckerService<Long, Member> memberExistCheckerService;
  private EntityExistCheckerService<Long, Product> productExistCheckerService;
  private EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> cartExistCheckerService;
  private StockCheckerService stockCheckerService;
  private UpdateCartService updateCartService;

  @Override
  public AllCartProductInfoDtoWithPagination getCartProductListByMemberPagination(Long memberId)
      throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();

    Long totalPaging = cartDao.getTheNumberOfTotalProductInCart(memberId, session);
    Pagination pagination =
        Pagination.builder()
            .currentPage(1)
            .totalPage(Integer.valueOf(Math.toIntExact(totalPaging)))
            .build();
    return AllCartProductInfoDtoWithPagination.getCartProductListWithPagination(
        this.getCartProductListByMember(memberId), pagination);
  }

  @Override
  public AllCartProductInfoDto getCartProductListByMember(Long memberId) throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    // MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session).getId();
    List<Cart> cartList = cartDao.getCartProductListByMember(memberId, session);
    List<Long> productIdList =
        cartList.stream().map(p -> p.getProductId()).collect(Collectors.toList());
    List<ProductItemQuantity> allProductInfo =
        productDao.selectProductQuantity(productIdList, session);
    return AllCartProductInfoDto.getCustomerViewOfCartInfo(
        ProductInCartDto.getProductInfo(allProductInfo, cartList));
  }

  @Override
  public void putItemIntoCart(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Long requestQuantity)
      throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKey.getMemberId();
    Long productId = productAndMemberCompositeKey.getProductId();
    // MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session);
    // ProductNotFoundException
    Product product = productExistCheckerService.isExisted(productDao, productId, session);
    Optional<Cart> cartOptional = cartDao.selectById(productAndMemberCompositeKey, session);
    if (cartOptional.isPresent()) {
      updateCartService.update(cartOptional.get(), requestQuantity, product.getQuantity(), session);
    } else {
      cartDao.insert(
          Cart.cartCompKeyBuilder(productAndMemberCompositeKey, requestQuantity), session);
    }
    session.commit();
    session.close();
  }

  @Override
  public void updateQuantityOfCartProduct(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Long requestUpdateQuantity)
      throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKey.getMemberId();
    Long productId = productAndMemberCompositeKey.getProductId();
    // MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session);
    // ProductNotFoundException
    Product product = productExistCheckerService.isExisted(productDao, productId, session);
    // CartNotFoundException
    Cart cart = cartExistCheckerService.isExisted(cartDao, productAndMemberCompositeKey, session);
    updateCartService.update(cart, requestUpdateQuantity, product.getQuantity(), session);
  }

  @Override
  public void delete(ProductAndMemberCompositeKey productAndMemberCompositeKey) throws Exception {
    SqlSession session = GetSessionFactory.getInstance().openSession();
    Long memberId = productAndMemberCompositeKey.getMemberId();
    Long productId = productAndMemberCompositeKey.getProductId();
    // MemberNotFoundException
    memberExistCheckerService.isExisted(memberDao, memberId, session);
    // ProductNotFoundException
    productExistCheckerService.isExisted(productDao, productId, session);
    // CartNotFoundException
    cartExistCheckerService.isExisted(cartDao, productAndMemberCompositeKey, session);

    cartDao.deleteById(productAndMemberCompositeKey, session);
  }
}
