package app.service.cart;

import app.dao.CartDaoFrame;
import app.dao.CartDaoFrameImpl;
import app.dao.product.ProductDao;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.error.exception.cart.OutOfStockException;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class AddCartServiceTest {

  private final TestConfig testConfig = new TestConfig();
  private final CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDaoFrameImpl();
  private final CartService cartService = new CartServiceImpl(cartDaoFrame,ProductDao.getInstance());
  private SqlSession session;


  @DisplayName("BeforeEach for checking input the item into cart. the product id 1 quantity is 2")
  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }
  @DisplayName("장바구니에 넣을 상품이 존재하지 않는 경우")
  @Test
  void insert_WhenProductIsNotExisted_ThrowProductNotExisted(){

  }

  @DisplayName("재고보다 많은 제품의 수량을 장바구니에 담는 경우의 테스트")
  @Test
  void insert_WhenQuantityIsBiggerThanRequestQuantity_Fail() throws Exception {
    ProductAndMemberCompositeKey compKey = new ProductAndMemberCompositeKey(1L, 1L);
    Assertions.assertThrows(OutOfStockException.class, () -> {
      cartService.putItemIntoCart(compKey, 5);
    });
  }

  @DisplayName("재고보다 적은 제품의 수량을 장바구니에 담는 경우의 테스트")
  @Test
  void insert_WhenQuantityIsLessThanRequestQuantity_Added() throws Exception {
    ProductAndMemberCompositeKey compKey = new ProductAndMemberCompositeKey(1L, 1L);
    cartService.putItemIntoCart(compKey, 1);
    Optional<Cart> actual = cartDaoFrame.selectById(compKey, session);
    Assertions.assertDoesNotThrow((Executable) actual::get, "inserted the cart as user want");
  }
}
