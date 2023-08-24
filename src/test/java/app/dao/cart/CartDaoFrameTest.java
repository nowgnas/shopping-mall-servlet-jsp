package app.dao.cart;

import static org.junit.jupiter.api.Assertions.assertThrows;

import app.dto.cart.CartAndProductDto;
import app.entity.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartDaoFrameTest {

  private final TestConfig testConfig = new TestConfig();
  CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDao();
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  void getInitDataWithoutCartData() throws Exception {
    testConfig.init("init-data.sql", session);
  }

  void getCartInitData() throws Exception {
    testConfig.init("cart-init.sql", session);
  }

  @DisplayName("멤버와 제품이 존재할 때 카트 데이터 넣기")
  @Test
  void insertCart_WhenThereIsMemberAndProduct_CartIsInserted() throws Exception {
    getInitDataWithoutCartData();
    Cart expectedCart = new Cart(1L, 1L, 1L);
    int expectedValue = cartDaoFrame.insert(expectedCart, session);
    Assertions.assertEquals(expectedValue, 1L);
  }

  @DisplayName("멤버와 제품이 존재하지 않을 때 카트 데이터 넣기")
  @Test
  void insertCart_WhenThereIsNotMemberAndProduct_SqlExceptionIsCatched() throws Exception {
    Cart expectedCart = new Cart(1L, 1L, 1L);
    Throwable throwable = assertThrows(PersistenceException.class, () -> {
      cartDaoFrame.insert(expectedCart, session);
    });
    Assertions.assertTrue(throwable.getCause() instanceof JdbcSQLIntegrityConstraintViolationException, "can not insert without member and product");
  }

  @DisplayName("멤버와 제품이 존재하지 않을 때 카트 데이터 삭제하기")
  @Test
  void deleteCart_WhenThereIsNotCart_return0() throws Exception {
    getInitDataWithoutCartData();
    Assertions.assertEquals(
        cartDaoFrame.deleteById(new ProductAndMemberCompositeKey(1L, 1L), session), 0);
  }

  @DisplayName("멤버와 제품이 존재할 때 카트 데이터 삭제하기")
  @Test
  void deleteCart_WhenThereIsCart_return1() throws Exception {
    getInitDataWithoutCartData();
    getCartInitData();
    Assertions.assertEquals(
        cartDaoFrame.deleteById(new ProductAndMemberCompositeKey(1L, 1L), session), 1);
  }

  @DisplayName("멤버와 제품이 존재할 때 단일 카트 조회하기")
  @Test
  void getOneCart_WhenThereIsCartByExistedMemberIdAndProductId_GetCart() throws Exception {
    getInitDataWithoutCartData();
    getCartInitData();
    Assertions.assertNotNull(
        cartDaoFrame.selectById(new ProductAndMemberCompositeKey(1L, 1L), session).get());
  }

  @DisplayName("멤버와 제품이 존재하지 않을 때 단일 카트 조회하기")
  @Test
  void getOneCart_WhenThereIsNotMemberIdOrProductId_False() throws Exception {
    getInitDataWithoutCartData();
    Assertions.assertThrowsExactly(NoSuchElementException.class, () -> {
      cartDaoFrame.selectById(new ProductAndMemberCompositeKey(1L, 1L),
          session).get();
    });

  }

  @DisplayName("멤버와 제품이 존재할 때 리스트 카트 조회하기")
  @Test
  void getAllCartByMemberId_WhenThereIsExistMemberIdAndProduct_CartList() throws Exception {
    getInitDataWithoutCartData();
    getCartInitData();
    List<Cart> cartList = cartDaoFrame.getCartProductListByMember(1L, session);
    Assertions.assertTrue(cartList.size() > 0);

  }

  @DisplayName("멤버와 제품이 존재하지 않을 때 리스트 카트 조회하기")
  @Test
  void getAllCartByMemberId_WhenThereIsNotCartByMemberId_CatchSqlException() throws Exception {
    getInitDataWithoutCartData();
    List<Cart> cartList = cartDaoFrame.getCartProductListByMember(1L, session);
    Assertions.assertEquals(0, cartList.size());
  }

  @DisplayName("카트의 row가 존재할 때 카트의 재고 업데이트하기")
  @Test
  void updateCartQuantity_WhenCartIsExisted_UpdateTheCartQuantity() throws Exception {
    getInitDataWithoutCartData();
    getCartInitData();
    Cart expected = new Cart(1L, 1L, 2L);
    cartDaoFrame.update(expected, session);
    Optional<Cart> actual = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(1L, 1L),
        session);
    Assertions.assertSame(expected.getProductQuantity(), actual.get().getProductQuantity());
  }

  @DisplayName("카트의 row가 존재하지 않을 때 카트의 재고 업데이트하기")
  @Test
  void updateCartQuantity_WhenCartIsNotExisted_Catch() throws Exception {
    getInitDataWithoutCartData();
    Cart expected = new Cart(1L, 1L, 2L);
    cartDaoFrame.update(expected, session);
    Optional<Cart> actual = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(1L, 1L),
        session);
    Assertions.assertThrowsExactly(NoSuchElementException.class, actual::get);
  }

  @DisplayName("멤버의 아이디로 카트의 존재하는 제품의 entity와 cart의 entity 얻기")
  @Test
  void getAllCartAndProductByMember_WhenThereIsProductsByMemberId_getList() throws Exception {
    getInitDataWithoutCartData();
    getCartInitData();
    List<CartAndProductDto> cartAndProductListDto = cartDaoFrame.getAllCartsAndAllProductsByMember(
        1L, session);
    System.out.println(cartAndProductListDto.get(0).toString());
    Assertions.assertNotNull(cartAndProductListDto);
  }


}
