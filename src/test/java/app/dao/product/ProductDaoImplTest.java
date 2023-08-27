package app.dao.product;

import app.dao.category.CategoryDao;
import app.dto.paging.Pagination;
import app.dto.product.ProductItemQuantity;
import app.dto.product.ProductListItem;
import app.dto.product.ProductListItemOfLike;
import app.dto.product.response.ProductSearchBySubCategory;
import app.enums.SortOption;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductDaoImplTest {
  private final TestConfig testConfig = new TestConfig();
  ProductDao productDao = ProductDao.getInstance();
  CategoryDao categoryDao = CategoryDao.getInstance();
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
  void selectProductListItemOfLike() throws Exception {
    List<Long> lst = Arrays.asList(1L, 2L, 3L);
    List<ProductListItemOfLike> productListItemOfLikes =
        productDao.selectProductListItemOfLike(lst, session);
    System.out.println(productListItemOfLikes.toString());
  }

  @Test
  void selectProductQuantity() {
    List<Long> lst = Arrays.asList(1L, 2L, 3L);
    List<ProductItemQuantity> product = productDao.selectProductQuantity(lst, session);
    System.out.println(product.toString());
    Assertions.assertEquals(lst.size(), product.size());
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
  @DisplayName("상품 리스트 조회 - 높은 가격 순")
  void selectAll() throws Exception {
    // case
    int perPage = 3;
    Long userId = -1L;
    SortOption dateDesc = SortOption.PRICE_DESC;

    Pagination pagination = Pagination.builder().currentPage(0).perPage(perPage).build();
    Map<String, Object> map = new HashMap<>();
    map.put("current", 0);
    map.put("perPage", perPage);
    map.put("userId", userId.toString());
    List<ProductListItem> products = productDao.selectAllSortByDate(map, session); // 모든 상품 조회
    System.out.println(products.toString());
    Assertions.assertEquals(perPage, products.size());
    session.close();
  }

  @Test
  @DisplayName("select product list by subcategory")
  void subcategoryProduct() {
    Map<String, Object> map = new HashMap<>();
    map.put("memberId", 1L);
    map.put("id", Arrays.asList(4L, 5L));
    List<ProductListItem> products =
        categoryDao.selectProductBySubCategoryName(map, session);
    //    List<ProductSearchBySubCategory> products =
    //        session.selectList("product.search-subcategory-product", map);
    System.out.println(products.toString());
  }
}
