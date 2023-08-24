package app.service.cart;

import app.dao.CartDaoFrame;
import app.dao.CartDaoFrameImpl;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.service.checker.CartExistCheckerService;
import app.service.checker.EntityExistCheckerService;
import app.service.checker.MemberExistCheckerService;
import app.service.checker.ProductExistCheckerService;
import app.service.product.StockCheckerService;
import app.service.product.StockCheckerServiceImpl;
import app.exception.cart.OutOfStockException;
import app.exception.member.MemberNotFoundException;
import app.exception.product.ProductNotFoundException;
import app.utils.GetSessionFactory;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PutItemIntoCartTest {

  private final TestConfig testConfig = new TestConfig();
  private final CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDaoFrameImpl();
  private final MemberDaoFrame<Long, Member> memberDao = new MemberDao();
  private final EntityExistCheckerService<Long, Member> memberExistCheckerService = new MemberExistCheckerService(
      memberDao);
  private final EntityExistCheckerService<Long, Product> productExistCheckerService = new ProductExistCheckerService();
  private final EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> cartExistCheckerService = new CartExistCheckerService();
  private final UpdateCartService updateCartService = new UpdateCartServiceImpl(cartDaoFrame,new DeleteCartWhenRestOfQuantityUnder0(cartDaoFrame));
  private final StockCheckerService stockCheckerService = new StockCheckerServiceImpl();
  private final CartService cartService = new CartServiceImpl(cartDaoFrame, memberDao,
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

  @DisplayName("재고보다 적은 제품의 수량을 장바구니에 담는 경우의 테스트")
  @Test
  void insert_WhenQuantityIsLessThanRequestQuantity_Added() throws Exception {
    insertAllInitData();
    ProductAndMemberCompositeKey compKey = new ProductAndMemberCompositeKey(1L, 1L);
    Assertions.assertDoesNotThrow(() -> cartService.putItemIntoCart(compKey, 1L),
        "inserted the cart as user want");
  }



}
