package web.controller;

import app.dto.product.response.ProductListWithPagination;
import app.dto.response.MemberDetail;
import app.service.category.CategoryService;
import app.service.category.CategoryServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.ControllerFrame;
import web.dispatcher.Navi;

public class CategoryController implements ControllerFrame {
  private final CategoryService service = CategoryServiceImpl.getInstance();

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String view = request.getParameter("view");

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

  private String build(HttpServletRequest request, String view) {
    String path = "redirect:index.jsp";
    if (view.equals("search")) {
      Long memberId = -1L;
      HttpSession session = request.getSession();
      MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
      if (loginMember != null) {
        memberId = loginMember.getId();
      }
      int curPage = Integer.parseInt(request.getParameter("curPage"));
      String keyword = request.getParameter("keyword");
      ProductListWithPagination productListByCategoryName =
          service.getProductListByCategoryName(memberId, keyword, curPage);
      request.setAttribute("productList", productListByCategoryName);
      return productListByCategorySearch();
    }
    return path;
    // fixme: 카테고리 조회를 할 필드가 없다.
  }

  private String productListByCategorySearch() {
    return Navi.FORWARD_CATEGORY_SEARCH;
  }
}
