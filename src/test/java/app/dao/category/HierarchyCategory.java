package app.dao.category;

import app.dto.category.response.CategoryHierarchy;
import app.service.category.CategoryService;
import app.service.category.CategoryServiceImpl;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HierarchyCategory {
  private final TestConfig testConfig = new TestConfig();
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

//  @Test
//  @DisplayName("hierarchy category service")
//  void hierarchy(){
//    List<CategoryHierarchy> hierarchyCategory = service.getHierarchyCategory();
//    System.out.println(hierarchyCategory.toString());
//  }
}
