package web.controller;

import app.entity.Address;
import app.entity.Cart;
import app.entity.Product;
import web.dispatcher.Navi;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** Servlet implementation class CustServlet */
@WebServlet({"/main"})
public class MainServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  public MainServlet() {
    super();
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
  }
}
