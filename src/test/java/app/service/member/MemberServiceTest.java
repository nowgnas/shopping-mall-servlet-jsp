package app.service.member;

import app.dao.member.MemberDao;
import app.dto.request.MemberRegisterDto;
import app.entity.Member;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import utils.GetSessionFactory;

import java.util.List;

class MemberServiceTest {

  private final MemberService memberService = new MemberService();
  private final MemberDao memberDao = new MemberDao();
  private final TestConfig testConfig = new TestConfig();
  private SqlSession session;

  @BeforeEach
  void beforeEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  @DisplayName("회원 가입 성공 테스트")
  void insert() throws Exception {
    // given
    String email = "abc@naver.com";
    String password = "123123";
    String name = "홍길동";

    MemberRegisterDto dto = new MemberRegisterDto(email, password, name);


    // when
    memberService.register(dto);

    // then
    List<Member> members = memberDao.selectAll(session);
    Member member = members.get(members.size() - 1);
    Assertions.assertEquals(email, member.getEmail());

  }
}
