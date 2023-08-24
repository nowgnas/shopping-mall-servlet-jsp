package app.dao.productorder;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.entity.ProductOrder;
import app.exception.ErrorCode;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductOrderDaoInsertTest {

  private ProductOrderDao productOrderDao = new ProductOrderDao();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("productorder/init-productorder-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("주문 상품 생성 테스트 - 정상 처리")
  void insert() throws Exception {
    // given
    ProductOrder productOrder =
        ProductOrder.builder().orderId(1L).productId(1L).price(2000000L).quantity(2L).build();

    // when
    int insertedRow = productOrderDao.insert(productOrder, session);
    session.commit();
    session.close();

    // then
    assertSame(1, insertedRow);
  }

  @Test
  @DisplayName("주문 상품 생성 테스트 - 존재하지 않는 주문")
  void insertEx1() throws Exception {
    // given
    ProductOrder productOrder =
        ProductOrder.builder().orderId(10000L).productId(1L).price(2000000L).quantity(2L).build();

    // when, then
    assertThrows(
        Exception.class,
        () -> productOrderDao.insert(productOrder, session),
        ErrorCode.CANNOT_INSERT_PRODUCT_ORDER.getMessage());
    session.commit();
    session.close();
  }

  @Test
  @DisplayName("주문 상품 모두 생성 테스트 - 정상 처리")
  void bulkInsert() throws Exception {
    // given
    List<ProductOrder> productOrders = new ArrayList<>();
    ProductOrder productOrder1 =
            ProductOrder.builder().orderId(1L).productId(1L).price(3000000L).quantity(1L).build();
    ProductOrder productOrder2 =
            ProductOrder.builder().orderId(1L).productId(2L).price(2000000L).quantity(2L).build();
    ProductOrder productOrder3 =
            ProductOrder.builder().orderId(1L).productId(3L).price(1500000L).quantity(2L).build();
    productOrders.add(productOrder1);
    productOrders.add(productOrder2);
    productOrders.add(productOrder3);

    // when, then
    int insertedRow = productOrderDao.bulkInsert(productOrders, session);
    assertSame(3, insertedRow);
    session.commit();
    session.close();
  }
}
