package app.service.cart;

import app.dao.CartDaoFrame;
import app.dao.CartDaoFrameImpl;
import app.dao.DaoFrame;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dao.product.ProductDao;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.entity.Member;
import app.error.ErrorCode;
import app.error.exception.cart.OutOfStockException;
import app.error.exception.cart.ProductIsNotExistedInCartException;
import app.error.exception.member.MemberNotFoundException;
import app.error.exception.product.ProductNotFoundException;
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
  private final MemberDaoFrame<Long, Member> memberDao = new MemberDao();
  private final CartService cartService = new CartServiceImpl(cartDaoFrame, memberDao);
  private SqlSession session;


  @DisplayName("BeforeEach for checking input the item into cart. the product id 1 quantity is 2")
  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
  }

  void insertProductData() throws Exception {
    testConfig.init("product-init.sql", session);
  }

  void insertDataWithoutProduct() throws Exception {
    testConfig.init("init-data-without-product.sql", session);

  }

  void insertAllInitData() throws Exception {
    testConfig.init("init-data.sql", session);
  }

  @DisplayName("넣을 장바구니에 고객 정보가 존재하지 않을 경우")
  @Test
  void insert_WhenUserIsNotExisted_ThrowMemberNotFoundException_CatchAnError() throws Exception {
    insertProductData();
    Cart expected = new Cart(1L, 1L, 1);
    Assertions.assertThrowsExactly(MemberNotFoundException.class, () -> {
      memberDao.selectById(1L, session).orElseThrow(() -> new MemberNotFoundException(
          ErrorCode.MEMBER_NOT_FOUND));
    });
  }


  @DisplayName("장바구니에 넣을 상품이 존재하지 않는 경우")
  @Test
  void insert_WhenProductIsNotExisted_ThrowProductException_CathAnError() throws Exception {
    insertDataWithoutProduct();
    Cart expected = new Cart(1L, 1L, 1);
    Assertions.assertThrowsExactly(ProductNotFoundException.class, () -> {
      cartService.putItemIntoCart(new ProductAndMemberCompositeKey(1L, 1L), 1);
    });
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
