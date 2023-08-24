package web.controller;

import app.dto.product.response.ProductDetailWithCategory;
import app.service.product.ProductService;
import app.service.product.ProductServiceImpl;
import web.ControllerFrame;
import web.dispatcher.Navi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductController implements ControllerFrame {
  private static final long serialVersionUID = 1L;
  private final ProductService service = ProductServiceImpl.getInstance();

  public ProductController() {
    super();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String view = request.getParameter("view");
    String cmd = request.getParameter("cmd");

    //    HttpSession session = request.getSession();
    //    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    //    Long memberId = loginMember.getId();

    String viewName = Navi.FORWARD_MAIN;
    if (view != null) {
      try {
        viewName = build(request, view);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    return viewName;
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
    return Navi.FORWARD_SHOP_DETAIL;
  }
}
