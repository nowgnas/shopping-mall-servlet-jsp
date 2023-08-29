package web.restController;

import app.dto.member.request.LoginDto;
import app.dto.member.request.MemberRegisterDto;
import app.dto.member.response.MemberDetail;
import app.exception.member.RegisterException;
import app.service.member.MemberService;
import web.RestControllerFrame;
import web.controller.validation.MemberValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class MemberRestController implements RestControllerFrame {
  private MemberService memberService;

  public MemberRestController() {
    super();
    memberService = new MemberService();
  }

  @Override
  public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String cmd = request.getParameter("cmd");
    Object result = "";

    if (cmd != null) {
      result = build(request, cmd);
    }
    return result;
  }

  private Object build(HttpServletRequest request, String cmd) throws Exception {
    Object result = null;
    switch (cmd) {
      case "loginCheck":
        return loginCheck(request);
      case "register":
        return register(request);
      case "login":
        return login(request);
      case "kakaoLogin":
        kakaoLogin(request);
    }
    return result;
  }

  private Boolean login(HttpServletRequest request) throws Exception {
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    LoginDto loginDto = new LoginDto(email, password);
    MemberDetail loginMember = memberService.login(loginDto);

    HttpSession session = request.getSession();
    session.setAttribute("loginMember", loginMember);
    return true;
  }

  private void kakaoLogin(HttpServletRequest request) throws IOException {
    String email = request.getParameter("email");
    String nickname = request.getParameter("nickname");
    MemberRegisterDto dto = new MemberRegisterDto(email, "00000000", nickname);

    MemberDetail memberDetail = memberService.kakaoLogin(dto);

    HttpSession session = request.getSession();
    session.setAttribute("loginMember", memberDetail);
  }

  private Object loginCheck(HttpServletRequest request) {
    String email = request.getParameter("email");
    return memberService.isDuplicatedEmail(email);
  }

  private Boolean register(HttpServletRequest request) {

    String email = request.getParameter("registerEmail");
    String password = request.getParameter("registerPassword");
    String name = request.getParameter("registerName");

    MemberRegisterDto dto = new MemberRegisterDto(email, password, name);

    if (!registerValidationCheck(dto)) {
      throw new RegisterException();
    }

    return memberService.register(dto);
  }

  private boolean registerValidationCheck(MemberRegisterDto dto) {
    if (!MemberValidation.isValidEmail(dto.getEmail())) {
      return false;
    }
    if (!MemberValidation.isValidPassword(dto.getPassword())) {
      return false;
    }
    if (!MemberValidation.isValidName(dto.getName())) {
      return false;
    }
    return true;
  }
}
