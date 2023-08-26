package app.service.product;

import app.dao.product.ProductDao;
import app.dto.product.response.ProductListWithPagination;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SearchProduct {
  private final TestConfig testConfig = new TestConfig();
  ProductDao productDao = ProductDao.getInstance();
  ProductService service = ProductServiceImpl.getInstance();
  SqlSession session;
  Logger log = Logger.getLogger("SearchProduct");

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
  void searchByKeyword() throws Exception {
    String word = "맥";
    ProductListWithPagination productsByKeyword = service.getProductsByKeyword(word, 1L, 0);
    log.info(productsByKeyword.toString());
    Assertions.assertEquals(2, productsByKeyword.getItem().size());
  }

  @Test
  @DisplayName("상품 이름 검색 - 상품 존재 하지 않음")
  void notExistProductName() throws Exception {
    String word = "pp";
    Exception exception =
        Assertions.assertThrows(
            Exception.class,
            () -> {
              ProductListWithPagination productsByKeyword =
                  service.getProductsByKeyword(word, 1L, 0);
            });
  }
}
