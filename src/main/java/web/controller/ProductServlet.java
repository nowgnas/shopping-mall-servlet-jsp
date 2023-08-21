package web.controller;

import app.service.product.ProductService;
import app.service.product.ProductServiceImpl;
import app.utils.HttpUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.dispatcher.Navi;

@WebServlet({"/product"})
public class ProductServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final ProductService service = ProductServiceImpl.getInstance();

  public ProductServlet() {
    super();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String view = request.getParameter("view");
    System.out.println(view + "---- view");
    String cmd = request.getParameter("cmd");

    //    HttpSession session = request.getSession();
    //    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    //    Long memberId = loginMember.getId();

    String viewName = "index.jsp";
    if (view != null) {
      viewName = build(request, view);
    }
    String path = viewName.substring(viewName.indexOf(":") + 1);

    if (path.startsWith("forward:")) {
      HttpUtil.forward(request, response, path);
    } else {
      HttpUtil.redirect(response, path);
    }
  }

  private String build(HttpServletRequest request, String view) {
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
