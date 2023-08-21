package app.dao.product;

import static org.junit.jupiter.api.Assertions.*;

import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

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
}
