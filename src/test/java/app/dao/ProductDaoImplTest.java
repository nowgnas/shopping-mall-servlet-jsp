package app.dao;

import app.entity.Product;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDaoImplTest {
  ProductDaoImpl productDao = new ProductDaoImpl();
  SqlSession session;
  private DataSource dataSource;
  private Connection connection;

  public ProductDaoImplTest() {
    this.session = GetSessionFactory.getInstance().openSession();
  }

  private final TestConfig testConfig = new TestConfig();

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
    List<Product> products = productDao.selectAllSortByPrice(session); // 모든 상품 조회
    for (Product p : products) {
      System.out.println(p.getPrice());
    }
    Assertions.assertEquals(8, products.size());
  }

  @Test
  void selectAllByDate() throws Exception {
    // case
    List<Product> products = productDao.selectAllSortByDate(session); // 모든 상품 조회
    for (Product p : products) {
      System.out.println(p.getCreatedAt());
    }
    Assertions.assertEquals(8, products.size());
  }
}
