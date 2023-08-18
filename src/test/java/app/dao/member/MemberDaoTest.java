package app.dao.member;

import app.dto.response.OrderMemberDetail;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;
import utils.GetSessionFactory;

class MemberDaoTest {

  private final MemberDao memberDao = new MemberDao();
  private final TestConfig testConfig = new TestConfig();
  private SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();

  @BeforeEach
  void beforeEach() throws Exception {
    SqlSession session = sessionFactory.openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    SqlSession session = sessionFactory.openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("주문 시 멤버와 관련된 기본 주소지 정보, 쿠폰 정보(리스트)를 같이 조회 한다.")
  void select() throws Exception {
    // given
    SqlSession session = sessionFactory.openSession();

    // when
    OrderMemberDetail orderMemberDetail = memberDao.selectAddressAndCouponById(1L, session).get();

    // then
    Assertions.assertEquals(1L, orderMemberDetail.getId());
  }
}
