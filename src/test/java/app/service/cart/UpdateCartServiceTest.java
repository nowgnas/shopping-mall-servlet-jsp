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
    increaseTheCartQuantityService.update(expected, requestExtraQuantity, stock, session);
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
        () -> increaseTheCartQuantityService.update(expected, requestExtraQuantity, stock,
            session));
  }

}
