package app.service.member;

import app.dao.member.MemberDao;
import app.dto.request.LoginDto;
import app.dto.request.MemberRegisterDto;
import app.dto.response.MemberDetail;
import app.entity.Member;
import app.exception.CustomException;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;
import utils.GetSessionFactory;

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
  void register_success() throws Exception {

    // given
    MemberRegisterDto dto = createMemberRegisterDto();

    // when
    memberService.register(dto);

    // then
    Member member = memberDao.selectByEmail(dto.getEmail(), session).get();
    Assertions.assertEquals(dto.getEmail(), member.getEmail());
  }

  @Test
  @DisplayName("회원 가입 중 중복 된 이메일로 가입 시도 시 예외가 발생한다.")
  void register_fail_duplicate_email() throws Exception {
    // given
    MemberRegisterDto dto = createMemberRegisterDto();
    String expectedMessage = "가입 된 이메일 입니다.";
    memberService.register(dto);
    // when
    CustomException customException =
        Assertions.assertThrows(
            CustomException.class,
            () -> {
              memberService.register(dto);
            });

    // then
    Assertions.assertEquals(expectedMessage, customException.getMessage());
  }

  @Test
  @DisplayName("회원 아이디(pk)로 내 정보 조회를 할 수 있다.")
  void getMemberDetail() throws Exception {
    // given
    Long id= 1L;
    // when
    MemberDetail memberDetail = memberService.get(id);
    // then
    Assertions.assertEquals(id, memberDetail.getId());
  }

  @Test
  @DisplayName("존재 하지 않는 회원 아이디로 조회 시 예외가 발생한다.")
  void getMemberDetail_fail() throws Exception {
    // given
    Long id= 10L;
    String expectedMessage = "해당 아이디의 회원은 존재 하지 않습니다.";
    // when
    CustomException customException =
            Assertions.assertThrows(
                    CustomException.class,
                    () -> {
                      memberService.get(id);
                    });
    // then
    Assertions.assertEquals(expectedMessage, customException.getMessage());
  }

  @Test
  @DisplayName("이메일 중복검사 성공 시 true를 반환한다.")
  void email_duplicate_success() throws Exception {
    // given
    String email = "testSuccess@naver.com";
    // when
    boolean result = memberService.isDuplicatedEmail(email);
    // then
    Assertions.assertTrue(result);
  }

  @Test
  @DisplayName("이메일 중복검사 실패 시 false를 반환한다.")
  void email_duplicate_fail() throws Exception {
    // given
    String email = "test@naver.com";
    // when
    boolean result = memberService.isDuplicatedEmail(email);
    // then
    Assertions.assertFalse(result);
  }

  @Test
  @DisplayName("데이터 베이스에 저장된 이메일과 패스워드로 로그인 성공 시 loginMember를 반환한다.")
  void login_success() throws Exception {
    // given
    MemberRegisterDto dto = createMemberRegisterDto();
    memberService.register(dto);

    LoginDto loginDto = new LoginDto(dto.getEmail(), dto.getPassword());
    // when
    MemberDetail loginMember = memberService.login(loginDto);
    // then
    Assertions.assertEquals(dto.getEmail(), loginMember.getEmail());
  }

  @Test
  void login_success123123() throws Exception {
    // given
    String email = "test@naver.com";
    String password = "test123";

  }

  private MemberRegisterDto createMemberRegisterDto() {
    String email = "user01@naver.com";
    String password = "user1234";
    String name = "비트롯데";
    return new MemberRegisterDto(email, password, name);
  }
}
