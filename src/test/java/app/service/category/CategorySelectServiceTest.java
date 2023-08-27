package app.service.category;

import app.dao.category.CategoryDao;
import app.dao.category.CategoryDaoFrame;
import app.dto.product.response.ProductListWithPagination;
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
  private final CategoryService service = CategoryServiceImpl.getInstance();
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
  @DisplayName("category select all service test - call dao")
  void selectAll() throws Exception {
    List<Category> list = dao.selectAll(session);
    Assertions.assertEquals(6, list.size());
    session.close();
  }

  @Test
  @DisplayName("category service select all and show data ")
  void selectAllAndData() throws Exception {
    List<Category> allCategory = service.getAllCategory();
    System.out.println(allCategory.toString());
    Assertions.assertEquals(6, allCategory.size());
  }

  @Test
  @DisplayName("select product list by category name")
  void selectProductByCategoryName() {
    ProductListWithPagination name = service.getProductListByCategoryName(1L, "컴퓨터", 0);
    System.out.println(name.toString());
  }
}
