package app.dao.cart;

import static org.junit.jupiter.api.Assertions.assertThrows;

import app.dao.CartDaoFrame;
import app.dao.CartDaoFrameImpl;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.sql.SQLException;
import java.util.Optional;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.modelmapper.internal.util.Assert;

class CartDaoTest {

  private final TestConfig testConfig = new TestConfig();
  CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDaoFrameImpl();
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
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
    Cart expectedCart = new Cart(1L, 1L, 1);
    int expectedValue = cartDaoFrame.insert(expectedCart, session);
    Assertions.assertEquals(expectedValue, 1);
  }


  @Test
  void insertCart_WhenThereIsNotMemberAndProduct_SqlExceptionIsCatched() throws Exception {
   Cart expectedCart = new Cart(1L, 1L, 1);
    Throwable throwable = assertThrows(PersistenceException.class, () -> {
        cartDaoFrame.insert(expectedCart, session);
    });
    Assertions.assertTrue(throwable.getCause() instanceof JdbcSQLIntegrityConstraintViolationException, "can not insert without member and product");
  }

  @Test
  void deleteCart_WhenThereIsNotCart_SqlExceptionIsCathed() throws Exception {

  }

  @Test
  void deleteCart_WhenThereIsCart_CartIsDeleted() throws Exception {
    getInitDataWithoutCartData();
    getCartInitData();
    cartDaoFrame.deleteById(new ProductAndMemberCompositeKey(1L, 1L), session);
  }

  @Test
  void getOneCart_WhenThereIsCartByExistedMemberIdAndProductId_GetCart() {


  }

  @Test
  void getOneCart_WhenThereIsNotMemberIdOrProductId_CatchSqlException() {

  }

  @Test
  void getAllCartByMemberId_WhenGetExistMemberId_CartList() {

  }

  @Test
  void getAllCartByMemberId_WhenThereIsNotCartByMemberId_CatchSqlException() {

  }

  @Test
  void updateCartQuantity_WhenCartIsExisted_UpdateTheCartQuantity() {

  }

  @Test
  void updateCartQuantity_WhenCartIsNotExisted_Catch() {

  }


}
