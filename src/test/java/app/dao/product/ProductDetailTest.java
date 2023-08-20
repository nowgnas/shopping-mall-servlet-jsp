package app.dao.product;

import app.dto.product.ProductDetail;
import app.dto.product.ProductDetailParameter;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductDetailTest {
  private final TestConfig testConfig = new TestConfig();
  ProductDao productDao = ProductDao.getInstance();
  Logger log = Logger.getLogger("hello");
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
    testConfig.init("product/product-likes.sql", session);
    testConfig.init("product/product-image.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("select product detail")
  void productDetail() {
    ProductDetail detail =
        session.selectOne(
            "product.select", ProductDetailParameter.builder().productId(2L).memberId(1L).build());
    log.info(detail.toString());
    session.close();
  }
}
