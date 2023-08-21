package app.dao.product;

import app.dto.product.response.ProductDetailWithCategory;
import app.entity.Category;
import app.service.product.ProductService;
import app.service.product.ProductServiceImpl;
import app.utils.GetSessionFactory;
import config.TestConfig;
import java.util.List;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductDetailServiceTest {
  private final TestConfig testConfig = new TestConfig();
  ProductDao productDao = ProductDao.getInstance();
  ProductService service = ProductServiceImpl.getInstance();
  Logger log = Logger.getLogger("hello");
  SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
//    testConfig.init("product/product-likes.sql", session);
    testConfig.init("product/product-image.sql", session);
    testConfig.init("product/product-category.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("product service detail test")
  void productDetail() throws Exception {
    ProductDetailWithCategory productDetail = service.getProductDetail(1L, 2L);
    log.info(productDetail.toString());
  }

  @Test
  @DisplayName("상품 상세 정보 조회 - 바로구매 시 정보 ")
  void productOrderDetail() {

    Exception exception =
        Assertions.assertThrows(
            Exception.class,
            () -> {
              int i = productDao.selectProductQuantity(1L, session);
              if (i < 3) throw new Exception("수량이 부족합니다");
            });
  }
}
