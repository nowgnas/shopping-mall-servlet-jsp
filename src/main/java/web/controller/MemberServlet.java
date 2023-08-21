package web.controller;

import app.dto.request.LoginDto;
import app.dto.request.MemberRegisterDto;
import app.dto.response.MemberDetail;
import app.error.CustomException;
import app.error.ErrorCode;
import app.service.member.MemberService;
import web.controller.validation.MemberValidation;

import javax.servlet.RequestDispatcher;
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
    String next = "index.jsp";
    String view = request.getParameter("view");
    if (view != null) {
      next = build(request, view);
    }

    RequestDispatcher rd = request.getRequestDispatcher(next);
    rd.forward(request, response);
  }

  private String build(HttpServletRequest request, String view) {
    String path = "redirect:index.jsp";
    switch (view) {
      case "registerForm":
        return registerForm();
      case "register":
        return register(request);
      case "loginForm":
        return loginForm();
      case "login":
        login(request);
        break;
      case "logout":
        logout(request);
        break;
    }
    return path;
  }

  private String registerForm() {
    return "forward:member/registerForm.jsp";
  }

  private String register(HttpServletRequest request) {

    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String name = request.getParameter("name");

    MemberRegisterDto dto = new MemberRegisterDto(email, password, name);

    if (!registerValidationCheck(dto)) {
      throw new CustomException(ErrorCode.REGISTER_FAIL);
    }

    memberService.register(dto);
    return "redirect:member/loginForm.jsp";
  }

  private String loginForm() {
    return "forward:member/loginForm.jsp";
  }

  private String login(HttpServletRequest request) {
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    LoginDto loginDto = new LoginDto(email, password);
    MemberDetail loginMember = memberService.login(loginDto);

    HttpSession session = request.getSession();
    session.setAttribute("loginMember", loginMember);
    return "redirect:index.jsp";
  }

  private String logout(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.invalidate();
    request.setAttribute("center", "index");
    return "redirect:index.jsp";
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
