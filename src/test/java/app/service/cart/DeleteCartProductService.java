package app.service.cart;

import app.dao.cart.CartDao;
import app.dao.cart.CartDaoImpl;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.entity.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.exception.cart.CartNotFoundException;
import app.exception.member.MemberNotFoundException;
import app.exception.product.ProductNotFoundException;
import app.service.checker.CartExistCheckerService;
import app.service.checker.EntityExistCheckerService;
import app.service.checker.MemberExistCheckerService;
import app.service.checker.ProductExistCheckerService;
import app.service.product.StockCheckerService;
import app.service.product.StockCheckerServiceImpl;
import app.utils.GetSessionFactory;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeleteCartProductService {

  private final TestConfig testConfig = new TestConfig();
  private final CartDao<ProductAndMemberCompositeKey, Cart> cartDao = new CartDaoImpl();
  private final MemberDaoFrame<Long, Member> memberDao = new MemberDao();
  private final EntityExistCheckerService<Long, Member> memberExistCheckerService = new MemberExistCheckerService(
      memberDao);
  private final EntityExistCheckerService<Long, Product> productExistCheckerService = new ProductExistCheckerService();
  private final EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> cartExistCheckerService = new CartExistCheckerService();
  private final UpdateCartService updateCartService = new UpdateCartServiceImpl(cartDao,
      new DeleteCartWhenRestOfQuantityUnder0(cartDao));
    private final StockCheckerService stockCheckerService = new StockCheckerServiceImpl();
  private final CartService cartService = new CartServiceImpl(cartDao, memberDao,
      memberExistCheckerService, productExistCheckerService, cartExistCheckerService,
      stockCheckerService, updateCartService);
  private SqlSession session;

  @DisplayName("init data")
  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @DisplayName("멤버가 존재 하지 않을 때 카트 상품 삭제")
  @Test
  void deleteCartProduct_MemberIsNotExisted_CatchMemberNotFoundException() {
    Assertions.assertThrowsExactly(MemberNotFoundException.class,
        () -> cartService.delete(new ProductAndMemberCompositeKey(1L, 100L), 2L));
  }

  @DisplayName("상품이 존재 하지 않을 때 카트 상품 삭제")
  @Test
  void deleteCartProduct_ProductIsNotExisted_CatchProductNotFoundException() {
    Assertions.assertThrowsExactly(ProductNotFoundException.class,
        () -> cartService.delete(new ProductAndMemberCompositeKey(1000L, 1L), 1L));


  }

  @DisplayName("멤버와 상품이 존재 하지 않을 때 카트 상품 삭제")
  @Test
  void deleteCartProduct_MemberAndProductAreNotExisted_CatchMemberNotFoundException() {
    Assertions.assertThrowsExactly(MemberNotFoundException.class,
        () -> cartService.delete(new ProductAndMemberCompositeKey(1000L, 10000L), 1L));
  }

    @DisplayName("카트가 존재하지 않을 때")
  @Test
  void deleteCartProduct_CartIsNotExisted_CatchProductNotFoundException() {
    Assertions.assertThrowsExactly(
        CartNotFoundException.class,
        () -> cartService.delete(new ProductAndMemberCompositeKey(4L, 1L), 1L));
  }

  //카트가 존재할 때 카트 삭제
    @DisplayName("카트가 존재할 때 카트 삭제")
  @Test
  void deleteCartProduct_CartIsExisted_DeleteCart() {
    Assertions.assertDoesNotThrow(
        () -> cartService.delete(new ProductAndMemberCompositeKey(1L, 1L), 1L));
  }

}
