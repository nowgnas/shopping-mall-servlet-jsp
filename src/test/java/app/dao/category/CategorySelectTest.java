package app.dao.category;

import app.dto.category.CategoryIdListItem;
import app.dto.product.ProductListItem;
import app.entity.Category;
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

public class CategorySelectTest {
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
  @DisplayName("select category")
  void selectLevel1Category() {
    List<Category> categories = session.selectList("category.first-category");
    Assertions.assertEquals(6, categories.size());
  }

  @Test
  @DisplayName("select product by category name - query ")
  void selectProductByCategoryName() {
    Map<String, Object> map = new HashMap<>();
    map.put("memberId", 1);
    map.put("keyword", "갤");
    List<ProductListItem> products = session.selectList("category.search-product-by-category", map);
    System.out.println(products.toString());
    Assertions.assertEquals(2, products.size());
  }

  @Test
  @DisplayName("select category id list")
  void selectCategoryIdListByName() {
    List<CategoryIdListItem> ids = session.selectList("category.select-subcategory", "컴퓨터");
    System.out.println(ids.toString());
    Assertions.assertEquals(4, ids.size());
    session.close();
  }
}
