package app.dao.product;

import app.dto.product.ProductDetail;
import app.dto.product.ProductDetailParameter;
import app.dto.product.response.ProductDetailForOrder;
import app.entity.Category;
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
    testConfig.init("product/product-category.sql", session);
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
            "product.select", ProductDetailParameter.builder().productId(4L).memberId(1L).build());
    log.info(detail.toString());
    session.close();
  }

  @Test
  @DisplayName("select product's category")
  void productCategory() {
    List<Category> categories = session.selectList("product.get-category", 7L);
    log.info(categories.toString());
    session.close();
  }

  @Test
  @DisplayName("상품 재고 확인")
  void productQuantity() {
    // 상세 페이지에서 주문 버튼 클릭 시 재고 정보 확인 후 상품 정보 반환
    int qty = session.selectOne("product.check-qty", 1L);
    Assertions.assertEquals(2, qty);
  }

  @Test
  @DisplayName("상품 상세 정보 반환 - 바로구매 시 ")
  void productDetailForOrder() {
    /**
     * 필요 정보
     *
     * <p>id name quantity url - thumbnail price
     */
    ProductDetailForOrder detail = session.selectOne("product.product-detail-for-order", 1L);
    Assertions.assertEquals(3000000, detail.getPrice());
  }
}
