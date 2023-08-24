package web.controller;

import app.service.member.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/rest"})
public class RestServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private MemberService memberService;

  public RestServlet() {
    super();
    memberService = new MemberService();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String cmd = request.getParameter("cmd");
    Object result = "";

    if (cmd != null) {
      result = build(request, cmd);
    }

    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/json;charset=UTF-8");
    response.getWriter().print(result);
  }

  private Object build(HttpServletRequest request, String cmd) {
    Object result = null;
    switch (cmd) {
      case "loginCheck":
        return loginCheck(request);
    }

    return result;
  }

  private Object loginCheck(HttpServletRequest request) {
    String email = request.getParameter("email");
    return memberService.isDuplicatedEmail(email);
  }
}
