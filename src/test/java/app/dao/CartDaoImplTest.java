package app.dao;

import app.dao.product.ProductDao;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartDaoImplTest {

  private final TestConfig testConfig = new TestConfig();
  CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDaoFrameImpl();
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
    testConfig.init("cart-mapper.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  void insert() throws Exception {
    Cart expectedCart = new Cart(1L, 1L, 1);
    cartDaoFrame.insert(expectedCart, session);
    Cart actual = cartDaoFrame.selectById(new ProductAndMemberCompositeKey(1L, 1L), session).get();

    Assertions.assertEquals(expectedCart, actual);


  }

  @Test
  void delete() {

  }

  @Test
  void select() {

  }

  @Test
  void selectAllByMember() {

  }

  @Test
  void selectAllDataFromCart() {

  }

}
