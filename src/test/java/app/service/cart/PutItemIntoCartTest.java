package app.service.cart;

import app.dao.cart.CartDao;
import app.dao.cart.CartDaoImpl;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.entity.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.exception.cart.OutOfStockException;
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

class PutItemIntoCartTest {

  private final TestConfig testConfig = new TestConfig();
  private final CartDao<ProductAndMemberCompositeKey, Cart> cartDao = new CartDaoImpl();
  private final MemberDaoFrame<Long, Member> memberDao = new MemberDao();
  private final EntityExistCheckerService<Long, Member> memberExistCheckerService = new MemberExistCheckerService(
      memberDao);
  private final EntityExistCheckerService<Long, Product> productExistCheckerService = new ProductExistCheckerService();
  private final EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> cartExistCheckerService = new CartExistCheckerService();
  private final UpdateCartService updateCartService = new UpdateCartServiceImpl(
      cartDao,new DeleteCartWhenRestOfQuantityUnder0(cartDao));
  private final StockCheckerService stockCheckerService = new StockCheckerServiceImpl();
  private final CartService cartService = new CartServiceImpl(cartDao, memberDao,
      memberExistCheckerService, productExistCheckerService, cartExistCheckerService,
      stockCheckerService,updateCartService);
  private SqlSession session;


  @DisplayName("init data")
  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
  }


  void insertAllInitData() throws Exception {
    testConfig.init("init-data.sql", session);
  }

  @DisplayName("넣을 장바구니에 고객 정보가 존재하지 않을 경우")
  @Test
  void insert_WhenUserIsNotExisted_ThrowMemberNotFoundException_CatchAnError() throws Exception {
    insertAllInitData();
    Assertions.assertThrowsExactly(MemberNotFoundException.class, () -> {
      cartService.putItemIntoCart(new ProductAndMemberCompositeKey(1L, 200L), 1L);
    });
  }


  @DisplayName("장바구니에 넣을 상품이 존재하지 않는 경우")
  @Test
  void insert_WhenProductIsNotExisted_ThrowProductException_CatchAnError() throws Exception {
    insertAllInitData();
    Assertions.assertThrowsExactly(ProductNotFoundException.class, () -> {
      cartService.putItemIntoCart(new ProductAndMemberCompositeKey(100L, 1L), 1L);
    });
  }

  @DisplayName("재고보다 많은 제품의 수량을 장바구니에 담는 경우의 테스트")
  @Test
  void insert_WhenQuantityIsBiggerThanRequestQuantity_Fail() throws Exception {
    insertAllInitData();
    ProductAndMemberCompositeKey compKey = new ProductAndMemberCompositeKey(1L, 1L);
    Assertions.assertThrows(OutOfStockException.class, () -> {
      cartService.putItemIntoCart(compKey, 5L);
    });
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @DisplayName("재고보다 적은 제품의 수량을 장바구니에 담는 경우의 테스트")
  @Test
  void insert_WhenQuantityIsLessThanRequestQuantity_Added() throws Exception {
    insertAllInitData();
    ProductAndMemberCompositeKey compKey = new ProductAndMemberCompositeKey(1L, 1L);
    Assertions.assertDoesNotThrow(() -> cartService.putItemIntoCart(compKey, 1L),
        "inserted the cart as user want");
  }


}
