package app.service.cart;

import app.dao.cart.CartDao;
import app.dao.cart.CartDaoFrame;
import app.entity.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.exception.cart.CartQuantityIsUnder0Exception;
import app.exception.cart.OutOfStockException;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UpdateCartServiceTest {

  private final TestConfig testConfig = new TestConfig();
  private final CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDao();


  private final UpdateCartService increaseTheCartQuantityService = new UpdateCartServiceImpl(
      cartDaoFrame, new DeleteCartWhenRestOfQuantityUnder0(cartDaoFrame));
  private final UpdateCartService updateCartServiceWithDeleteStrategyWhenCartQuantityUnder0 = new UpdateCartServiceImpl(
      cartDaoFrame, new DeleteCartWhenRestOfQuantityUnder0(cartDaoFrame));
  private final UpdateCartService updateCartServiceWithThrowErrorWhenCartQuantityUnder0 = new UpdateCartServiceImpl(
      cartDaoFrame, new ThrowExceptionUserRequestOverProductQuantityInCart(cartDaoFrame));

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


  @DisplayName("이미 장바구니에 존재하는 상품을 장바구니에 존재하는 상품의 개수와 추가 요청한 개수의 상품의 합보다 재고의 개수가 더 적은 경우")
  @Test
  void updateCartQuantity_WhenThereIsAlreadyProductInCartAndProductStockIsSufficient_CartQuantityIsIncreased()
      throws Exception {
    Cart expected = new Cart(1L, 1L, 1L);
    Long requestExtraQuantity = 1L;
    Long stock = 2L;
    increaseTheCartQuantityService.increaseQuantity(expected, requestExtraQuantity, stock, session);
    Cart actual = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(1L, 1L), session).get();
    Assertions.assertEquals(expected.getProductQuantity() + requestExtraQuantity,
        actual.getProductQuantity());
  }

  @DisplayName("이미 장바구니에 존재하는 상품을 상품의 재고보다 많게 장바구니에 담는 경우의 테스트")
  @Test
  void updateCartQuantity_WhenThereIsAlreadyProductInCartAndProductStockIsSufficient_CatchOutOfStockException() {
    Cart expected = new Cart(1L, 1L, 1L);
    Long requestExtraQuantity = 3L;
    Long stock = 2L;

    Assertions.assertThrowsExactly(OutOfStockException.class,
        () -> increaseTheCartQuantityService.increaseQuantity(expected, requestExtraQuantity, stock,
            session));
  }


  @DisplayName("감소해도 카트의 총 개수가 1개 이상인 경우, 카트를 지우는 전략 사용")
  @Test
  void decreaseProductCartQuantity_whenResultOfCartProductQuantityOver0_decreaseCartQauntity()
      throws Exception {
    Cart expectedCart = new Cart(1L, 2L, 2L);
    Long expectedQuantity = 2L;
    Long requestExtraQuantity = 1L;
    updateCartServiceWithDeleteStrategyWhenCartQuantityUnder0.decreaseQuantity(expectedCart,
        requestExtraQuantity, session);
    Long actualQuantity = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(2L, 1L), session)
        .get().getProductQuantity();
    Assertions.assertEquals(expectedQuantity - requestExtraQuantity, actualQuantity);
  }

  @DisplayName("감소해도 카트의 총 개수가 1개 이상인 경우, 업데이트 개수가 0개 미만일 경우에러를 던지는 전략 사용")
  @Test
  void decreaseProductCartQuantity_WhenResultOfCartProductQuantityOver0WithUsingErrorStrategy_decreaseCartQauntity()
      throws Exception {
   Cart expectedCart = new Cart(1L, 2L, 2L);
    Long expectedQuantity = 2L;
    Long requestExtraQuantity = 1L;
    updateCartServiceWithThrowErrorWhenCartQuantityUnder0.decreaseQuantity(expectedCart,
        requestExtraQuantity, session);
    Long actualQuantity = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(2L, 1L), session)
        .get().getProductQuantity();
    Assertions.assertEquals(expectedQuantity - requestExtraQuantity, actualQuantity);
  }

  @DisplayName("감소했을 때 개수가 0개인 경우, 카트를 지우는 전략을 사용")
  @Test
  void decreaseProductCartQuantity_whenResultOfCartProductQuantityEqual0WithDeleteCartStrategy_deleteCart()
      throws Exception {
Cart expectedCart = new Cart(1L, 2L, 2L);
    Long requestExtraQuantity = 2L;
    updateCartServiceWithDeleteStrategyWhenCartQuantityUnder0.decreaseQuantity(expectedCart,
        requestExtraQuantity, session);
    Optional<Cart> cartOptional = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(2L, 1L), session);
    Assertions.assertThrowsExactly(NoSuchElementException.class, cartOptional::get);
  }

   @DisplayName("감소했을 때 개수가 0개 미만인 경우, 카트를 지우는 전략을 사용")
  @Test
  void decreaseProductCartQuantity_whenResultOfCartProductQuantityUnder0_deleteCart()
      throws Exception {
Cart expectedCart = new Cart(1L, 2L, 2L);
    Long requestExtraQuantity = 3L;
    updateCartServiceWithDeleteStrategyWhenCartQuantityUnder0.decreaseQuantity(expectedCart,
        requestExtraQuantity, session);
    Optional<Cart> cartOptional = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(2L, 1L), session);
    Assertions.assertThrowsExactly(NoSuchElementException.class, cartOptional::get);
  }


  @DisplayName("감소했을 때 개수가 0개이하 경우, 에러를 던지는 전략 사용")
  @Test
  void decreaseProductCartQuantity_whenResultOfCartProductQuantityUnder0_throwAnError() {
    Cart expectedCart = new Cart(1L, 2L, 2L);
    Long requestExtraQuantity = 2L;
    Assertions.assertThrowsExactly(CartQuantityIsUnder0Exception.class,
        () -> updateCartServiceWithThrowErrorWhenCartQuantityUnder0.decreaseQuantity(expectedCart,
            requestExtraQuantity, session));
  }

}
