package app.service.category;

import app.dao.category.CategoryDao;
import app.dao.category.CategoryDaoFrame;
import app.entity.Category;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategorySelectServiceTest {
  private final TestConfig testConfig = new TestConfig();
  private final CategoryDaoFrame dao = CategoryDao.getInstance();
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
    testConfig.init("category/category.sql", session);
    testConfig.init("product/product-image.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("category select all service test")
  void selectAll() throws Exception {
    List<Category> list = dao.selectAll(session);
    Assertions.assertEquals(6, list.size());
    session.close();
  }
}
