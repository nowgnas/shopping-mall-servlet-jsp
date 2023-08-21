package web.controller;

import app.dto.response.MemberDetail;
import app.service.product.ProductService;
import app.service.product.ProductServiceImpl;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.dispatcher.Navi;

@WebServlet({"/product"})
public class ProductServlet {
  private static final long serialVersionUID = 1L;
  private final ProductService service = ProductServiceImpl.getInstance();

  public ProductServlet() {
    super();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String view = request.getParameter("view");
    System.out.println(view+ "---- view");
    String cmd = request.getParameter("cmd");

    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    //    Long memberId = loginMember.getId();

    String viewName = "index.jsp";
    if (view != null && cmd != null) {
      viewName = build(request, view, cmd, 6L);
    }

    RequestDispatcher rd = request.getRequestDispatcher(viewName);
    rd.forward(request, response);
  }

  private String build(HttpServletRequest request, String view, String cmd, Long memberId) {
    String path = "redirect:index.jsp";
    switch (view) {
      case "detail":
        return productDetail();
    }
    return path;
  }

  private String productDetail() {
    return Navi.REDIRECT_DETAIL;
  }
}
