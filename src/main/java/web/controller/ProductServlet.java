package web.controller;

import app.dto.product.response.ProductDetailWithCategory;
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
    String cmd = request.getParameter("cmd");

    //    HttpSession session = request.getSession();
    //    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    //    Long memberId = loginMember.getId();

    String viewName = "index.jsp";
    if (view != null) {
      try {
        viewName = build(request, view);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    String path = viewName.substring(viewName.indexOf(":") + 1);

    if (viewName.startsWith("forward:")) {
      HttpUtil.forward(request, response, path);
    } else {
      HttpUtil.redirect(response, path);
    }
  }

  private String build(HttpServletRequest request, String view) throws Exception {
    String path = "redirect:index.jsp";
    if (view.equals("shop-detail")) {
      ProductDetailWithCategory productDetail = service.getProductDetail(6L, 1L);
      request.setAttribute("productDetail", productDetail);
      return productDetail();
    }
    return path;
  }

  private String productDetail() {
    return Navi.FORWARD_DETAIL;
  }
}
