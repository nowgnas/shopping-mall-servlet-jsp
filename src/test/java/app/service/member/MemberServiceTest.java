package app.service.member;

import app.dao.member.MemberDao;
import app.dto.member.request.LoginDto;
import app.dto.member.request.MemberRegisterDto;
import app.dto.member.response.MemberDetail;
import app.entity.Member;
import app.exception.member.DuplicatedEmailException;
import app.exception.member.MemberEntityNotFoundException;
import config.TestConfig;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.*;
import utils.GetSessionFactory;

class MemberServiceTest {

  private static final MemberDao memberDao = new MemberDao();
  private static final TestConfig testConfig = new TestConfig();
  private static SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private final MemberService memberService = new MemberService();

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
  @DisplayName("회원 가입 성공 테스트")
  void register_success() throws Exception {
    SqlSession session = sessionFactory.openSession();
    // given
    MemberRegisterDto dto = createMemberRegisterDto();

    // when
    memberService.register(dto);

    // then
    Member member = memberDao.selectByEmail(dto.getEmail(), session).get();
    session.close();
    Assertions.assertEquals(dto.getEmail(), member.getEmail());
  }

  @Test
  @DisplayName("회원 가입 중 중복 된 이메일로 가입 시도 시 예외가 발생한다.")
  void register_fail_duplicate_email() throws Exception {
    // given
    MemberRegisterDto dto = createMemberRegisterDto();
    String expectedMessage = "중복 된 이메일 입니다.";
    memberService.register(dto);
    // when
    DuplicatedEmailException exception =
        Assertions.assertThrows(
            DuplicatedEmailException.class,
            () -> {
              memberService.register(dto);
            });

    // then
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  @DisplayName("회원 아이디(pk)로 내 정보 조회를 할 수 있다.")
  void getMemberDetail() throws Exception {
    // given
    MemberRegisterDto memberRegisterDto = createMemberRegisterDto();
    memberService.register(memberRegisterDto);
    SqlSession session = sessionFactory.openSession();
    Member member = memberDao.selectByEmail(memberRegisterDto.getEmail(), session).get();

    // when
    MemberDetail memberDetail = memberService.get(member.getId());
    // then
    Assertions.assertEquals(member.getId(), memberDetail.getId());
  }

  @Test
  @DisplayName("존재 하지 않는 회원 아이디로 조회 시 예외가 발생한다.")
  void getMemberDetail_fail() throws Exception {
    // given
    Long id = 11111L;
    String expectedMessage = "회원 정보를 찾을 수 없습니다.";
    // when
    MemberEntityNotFoundException exception =
        Assertions.assertThrows(
            MemberEntityNotFoundException.class,
            () -> {
              memberService.get(id);
            });
    // then
    Assertions.assertEquals(expectedMessage, exception.getMessage());
  }

  @Test
  @DisplayName("이메일 중복 검사 후 중복된 이메일이 없는 경우 true를 반환 한다.")
  void email_duplicate_success() throws Exception {
    // given
    MemberRegisterDto memberRegisterDto = createMemberRegisterDto();
    memberService.register(memberRegisterDto);
    String email = "user0003@naver.com";
    // when
    boolean result = memberService.isDuplicatedEmail(email);
    // then
    Assertions.assertTrue(result);
  }

  @Test
  @DisplayName("이메일 중복 검사 후 중복된 이메일이 있는 경우 false를 반환한다.")
  void email_duplicate_fail() throws Exception {
    // given
    MemberRegisterDto memberRegisterDto = createMemberRegisterDto();
    memberService.register(memberRegisterDto);
    String email = "user01@naver.com";
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

  private MemberRegisterDto createMemberRegisterDto() {
    String email = "user01@naver.com";
    String password = "user1234";
    String name = "비트롯데";
    return new MemberRegisterDto(email, password, name);
  }
}
