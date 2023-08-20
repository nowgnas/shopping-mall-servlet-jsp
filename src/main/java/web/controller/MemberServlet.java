package web.controller;

import app.dto.request.MemberRegisterDto;
import app.service.member.MemberService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
      build(request, view);
    }

    RequestDispatcher rd = request.getRequestDispatcher(next);
    rd.forward(request, response);
  }

  private void build(HttpServletRequest request, String view) {
    switch (view) {
      case "registerForm":
        request.setAttribute("center", "registerForm");
        break;
      case "loginForm":
        request.setAttribute("center", "loginForm");
        break;
      case "register":
        register(request);
        break;
      case "login":
        break;
      case "logout":
        break;
    }
  }

  private void register(HttpServletRequest request) {
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String name = request.getParameter("name");

    MemberRegisterDto dto = new MemberRegisterDto(email, password, name);
    memberService.register(dto);
    request.setAttribute("center", "login");
  }
}
