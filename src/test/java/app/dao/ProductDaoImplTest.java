package app.dao;

import app.dao.product.ProductDao;
import app.dto.product.ProductItemQuantity;
import app.dto.product.ProductListItem;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.Arrays;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDaoImplTest {
  ProductDao productDao = ProductDao.getInstance();
  SqlSession session;

  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
    testConfig.init("product-image.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  void selectProductQuantity() {
    List<Long> lst = Arrays.asList(1L, 2L, 3L);
    List<ProductItemQuantity> product = productDao.selectProductQuantity(lst, session);
    System.out.println(product.toString());
  }

  @Test
  void insert() {
    // product 추가는 없다
  }

  @Test
  void update() {}

  @Test
  void deleteById() {}

  @Test
  void selectById() {
    // 상품 id를 이용한 상품 조회

  }

  @Test
  void selectAll() throws Exception {
    // case
    List<ProductListItem> products = productDao.selectAllSortByPriceDesc(session); // 모든 상품 조회
    for (ProductListItem p : products) {
      System.out.println(p.getPrice());
    }
    Assertions.assertEquals(8, products.size());
  }

  @Test
  void selectAllByDate() throws Exception {
    // case
    List<ProductListItem> products = productDao.selectAllSortByDate(session); // 모든 상품 조회
    for (ProductListItem p : products) {
      System.out.println(p.getCreatedAt());
    }
    Assertions.assertEquals(8, products.size());
  }
}
