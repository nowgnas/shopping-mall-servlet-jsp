package web.controller;

import app.dto.request.LoginDto;
import app.dto.request.MemberRegisterDto;
import app.dto.response.MemberDetail;
import app.exception.CustomException;
import app.exception.ErrorCode;
import app.service.member.MemberService;
import app.utils.HttpUtil;
import web.controller.validation.MemberValidation;
import web.dispatcher.Navi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet({"/member"})
public class MemberServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final MemberService memberService;

  public MemberServlet() {
    super();
    memberService = new MemberService();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String next = Navi.REDIRECT_MAIN;
    String view = request.getParameter("view");
    if (view != null) {
      next = build(request, view);
    }

    String path = next.substring(next.indexOf(":") + 1);
    if (next.startsWith("forward:")) {
      HttpUtil.forward(request, response, path);
    } else {
      HttpUtil.redirect(response, path);
    }
  }

  private String build(HttpServletRequest request, String view) {
    String path = Navi.FORWARD_MAIN;
    switch (view) {
      case "registerForm":
        return registerForm();
      case "register":
        return register(request);
      case "loginForm":
        return loginForm();
      case "login":
        return login(request);
      case "logout":
        return logout(request);
    }
    return path;
  }

  private String registerForm() {
    return Navi.FORWARD_REGISTER_FORM;
  }

  private String register(HttpServletRequest request) {

    String email = request.getParameter("registerEmail");
    String password = request.getParameter("registerPassword");
    String name = request.getParameter("registerName");

    MemberRegisterDto dto = new MemberRegisterDto(email, password, name);

    if (!registerValidationCheck(dto)) {
      throw new CustomException(ErrorCode.REGISTER_FAIL);
    }

    memberService.register(dto);

    return Navi.REDIRECT_MAIN;
  }

  private String loginForm() {
    return Navi.FORWARD_LOGIN_FORM;
  }

  private String login(HttpServletRequest request) {
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    LoginDto loginDto = new LoginDto(email, password);
    MemberDetail loginMember = memberService.login(loginDto);

    HttpSession session = request.getSession();
    session.setAttribute("loginMember", loginMember);
    return Navi.REDIRECT_MAIN;
  }

  private String logout(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.invalidate();
    request.setAttribute("center", "index");
    return Navi.REDIRECT_MAIN;
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
