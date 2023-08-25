package app.dao.member;

import app.dto.response.OrderMemberDetail;
import app.entity.Member;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;
import utils.GetSessionFactory;

class MemberDaoTest {

  private static final MemberDao memberDao = new MemberDao();
  private static final TestConfig testConfig = new TestConfig();
  private static SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();

  @BeforeAll
  static void beforeAll() throws Exception {
    SqlSession session = sessionFactory.openSession();
    testConfig.init("schema.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    SqlSession session = sessionFactory.openSession();
    testConfig.init("member/member-clear.sql", session);
  }

  @Test
  @DisplayName("주문 시 멤버와 관련된 기본 주소지 정보, 쿠폰 정보(리스트)를 같이 조회 한다.")
  void select() throws Exception {
    // given
    SqlSession session = sessionFactory.openSession();
    Member member = createMember();
    memberDao.insert(member, session);
    session.commit();
    // when
    OrderMemberDetail orderMemberDetail = memberDao.selectAddressAndCouponById(1L, session).get();

    // then
    Assertions.assertEquals(1L, orderMemberDetail.getId());
  }

  @Test
  @DisplayName("입력받은 이메일 데이터베이스에 존재하는지 검사한다. 존재하면 1을 반환한다.")
  void countByEmail_result_1() throws Exception {
    // given
    SqlSession session = sessionFactory.openSession();
    Member member = createMember();
    memberDao.insert(member, session);
    session.commit();
    int expectedResult = 1;

    // when
    SqlSession newSession = sessionFactory.openSession();
    int result = memberDao.countByEmail(member.getEmail(), newSession);

    // then
    Assertions.assertEquals(expectedResult, result);
  }

  @Test
  @DisplayName("입력받은 이메일 데이터베이스에 존재하는지 검사한다. 존재 하지 않으면 0을 반환한다.")
  void countByEmail_result_0() throws Exception {
    // given
    SqlSession session = sessionFactory.openSession();
    Member member = createMember();
    memberDao.insert(member, session);
    session.commit();;
    int expectedResult = 0;

    // when
    SqlSession newSession = sessionFactory.openSession();
    int result = memberDao.countByEmail("test02", newSession);

    // then
    Assertions.assertEquals(expectedResult, result);
  }

  private Member createMember() {
    return Member.builder().email("user01").password("test").name("테스트").build();
  }
}
