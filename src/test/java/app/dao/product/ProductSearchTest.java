package app.dao.product;

import app.entity.Product;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductSearchTest {
  private final TestConfig testConfig = new TestConfig();
  ProductDao productDao = ProductDao.getInstance();
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
    //    testConfig.init("product/product-likes.sql", session);
    testConfig.init("product/product-image.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("상품 이름 검색 ")
  void searchProduct() {
    String keyword = "맥";
    List<Product> products = session.selectList("product.searchByWord", keyword);
    System.out.println(products.size());
  }
}
