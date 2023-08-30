package web.controller;

import app.dto.product.response.ProductDetailWithCategory;
import app.dto.product.response.ProductListWithPagination;
import app.dto.member.response.MemberDetail;
import app.entity.Category;
import app.service.category.CategoryService;
import app.service.category.CategoryServiceImpl;
import app.service.product.ProductService;
import app.service.product.ProductServiceImpl;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.ControllerFrame;
import web.dispatcher.Navi;

public class ProductController implements ControllerFrame {
  private static final long serialVersionUID = 1L;
  private final ProductService service = ProductServiceImpl.getInstance();
  private final CategoryService categoryService = CategoryServiceImpl.getInstance();
  Logger log = Logger.getLogger("ProductController");

  public ProductController() {
    super();
  }

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

  private String build(HttpServletRequest request, String view) throws Exception {
    String path = "redirect:index.jsp";
    if (view.equals("shop-detail")) {
      String productId = request.getParameter("productId");
      Long memberId = -1L;
      HttpSession session = request.getSession();
      MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
      if (loginMember != null) {
        memberId = loginMember.getId();
      }
      ProductDetailWithCategory productDetail =
          service.getProductDetail(memberId, Long.valueOf(productId));
      request.setAttribute("productDetail", productDetail);
      return productDetail();
    } else if (view.equals("shop")) {
      Long memberId = -1L;
      HttpSession session = request.getSession();
      MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
      if (loginMember != null) {
        memberId = loginMember.getId();
      }
      List<Category> categories = categoryService.getAllCategory();
      int curPage = Integer.parseInt(request.getParameter("curPage"));
      String sort = request.getParameter("sort");
      ProductListWithPagination productList = service.getProductList(memberId, curPage, sort);
      request.setAttribute("categories", categories);
      request.setAttribute("productList", productList);
      return productList();
    } else if (view.equals("search")) {
      Long memberId = -1L;
      HttpSession session = request.getSession();
      MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
      if (loginMember != null) {
        memberId = loginMember.getId();
      }
      List<Category> categories = categoryService.getAllCategory();
      int curPage = Integer.parseInt(request.getParameter("curPage"));
      String keyword = request.getParameter("keyword");
      ProductListWithPagination productsByKeyword =
          service.getProductsByKeyword(keyword, memberId, curPage);

      System.out.println(productsByKeyword.getItem().size() + " 개?");

      request.setAttribute("categories", categories);
      request.setAttribute("productList", productsByKeyword);
      return productList();
    } else if (view.equals("category")) {
      Long memberId = -1L;
      HttpSession session = request.getSession();
      MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
      if (loginMember != null) {
        memberId = loginMember.getId();
      }
      String keyword = request.getParameter("keyword");
      String curPage = request.getParameter("curPage");
      log.info("view : " + view + " keyword : " + keyword + " curpage : " + curPage);
      ProductListWithPagination productListByCategoryName =
          categoryService.getProductListByCategoryName(
              memberId, keyword, Integer.parseInt(curPage));
      List<Category> categories = categoryService.getAllCategory();
      request.setAttribute("categories", categories);
      request.setAttribute("productList", productListByCategoryName);
      return productList();
    }
    return path;
  }

  private String productList() {
    return Navi.FORWARD_PRODUCT_DETAIL;
  }

  private String productDetail() {
    return Navi.FORWARD_SHOP_DETAIL;
  }
}
