package web.controller;

import app.utils.HttpUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.dispatcher.Navi;

/**
 * Servlet implementation class CustServlet
 */
@WebServlet({"/main"})
public class MainServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public MainServlet() {
    super();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String next = Navi.FORWARD_MAIN;
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
//    if (view.equals("register")) {
//      request.setAttribute("center", "register");
//      request.setAttribute("navi", Navi.register);
//    } else if (view.equals("login")) {
//      request.setAttribute("center", "login");
//      request.setAttribute("navi", Navi.login);
//    } else if (view.equals("custadd")) {
//      request.setAttribute("center", "app/cust/register");
//    } else if (view.equals("productadd")) {
//      request.setAttribute("center", "product/register");
//    } else if (view.equals("chart")) {
//      request.setAttribute("center", "chart/chart");
//    }
    return null;
  }
}
