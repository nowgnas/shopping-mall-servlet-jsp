package app.dao.product;

import static org.junit.jupiter.api.Assertions.*;

import app.entity.ProductImage;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductImageDaoTest {
  private final TestConfig testConfig = new TestConfig();
  Logger log = Logger.getLogger("ProductImageDaoTest");
  ProductImageDao dao = ProductImageDao.getInstance();
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
    testConfig.init("product-image.sql", session);
    log.info("image inserted");
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  void selectById() throws Exception {
    // TODO: 데이터 값이 올바르게 나오지 않음
    Optional<ProductImage> productImage = dao.selectById(1L, session);
    log.info(productImage.get().toString());
    Assertions.assertEquals(1L, productImage.get().getProductId());
  }
}
