package app.dao.product;

import app.dto.product.ProductListItem;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
    Map<String, Object> map = new HashMap<>();
    map.put("userId", 1L);
    map.put("current", 0);
    map.put("keyword", keyword);
    List<ProductListItem> products = session.selectList("product.searchByWord", map);
    Assertions.assertEquals(2, products.size());
  }
}
