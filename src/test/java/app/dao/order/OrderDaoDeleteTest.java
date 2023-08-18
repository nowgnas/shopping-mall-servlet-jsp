package app.dao.order;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.error.ErrorCode;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

public class OrderDaoDeleteTest {

  private final OrderDao orderDao = new OrderDao();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("order/init-order-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("주문 삭제 테스트 - 정상 처리")
  void deleteById() throws Exception {
//    // given
//    Long orderId = 1L;
//
//    // when
//    int deletedRow = orderDao.deleteById(orderId, session);
//    session.commit();
//    session.close();
//
//    // then
//    assertSame(1, deletedRow);
  }

  @Test
  @DisplayName("주문 삭제 테스트 - 존재하지 않는 사용자")
  void deleteByIdEx1() throws Exception {
//    // given
//    Long orderId = 1000L;
//
//    // when, then
//    assertThrows(
//        Exception.class,
//        () -> orderDao.deleteById(orderId, session),
//        ErrorCode.CANNOT_DELETE_ORDER.getMessage());
//    session.commit();
//    session.close();
  }
}
