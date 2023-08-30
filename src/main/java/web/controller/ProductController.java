package web.controller;

import app.dto.member.response.MemberDetail;
import app.dto.product.response.ProductDetailWithCategory;
import app.dto.product.response.ProductListWithPagination;
import app.entity.Category;
import app.exception.product.ProductNotFoundException;
import app.service.category.CategoryService;
import app.service.category.CategoryServiceImpl;
import app.service.product.ProductService;
import app.service.product.ProductServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.ControllerFrame;
import web.dispatcher.Navi;

public class ProductController implements ControllerFrame {
  private static final long serialVersionUID = 1L;
  private final ProductService service = ProductServiceImpl.getInstance();
  private final CategoryService categoryService = CategoryServiceImpl.getInstance();
  private Long memberId = -1L;

  public ProductController() {
    super();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String view = request.getParameter("view");

    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
    if (loginMember != null) {
      memberId = loginMember.getId();
    }

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
    switch (view) {
      case "shop-detail":
        return productDetail(request);
      case "shop":
        return productList(request);
      case "search":
        return searchProductByKeyword(request);
      case "category":
        return productListOfCategory(request);
    }
    return Navi.REDIRECT_MAIN;
  }

  private String productListOfCategory(HttpServletRequest request) throws Exception {
    String keyword = request.getParameter("keyword");
    String curPage = request.getParameter("curPage");
    ProductListWithPagination productListByCategoryName =
        categoryService.getProductListByCategoryName(memberId, keyword, Integer.parseInt(curPage));
    List<Category> categories = categoryService.getAllCategory();
    request.setAttribute("categories", categories);
    request.setAttribute("productList", productListByCategoryName);
    return productList();
  }

  private String searchProductByKeyword(HttpServletRequest request) throws Exception {
    List<Category> categories = categoryService.getAllCategory();
    int curPage = Integer.parseInt(request.getParameter("curPage"));
    String keyword = request.getParameter("keyword");
    ProductListWithPagination productsByKeyword = null;
    try {
      productsByKeyword = service.getProductsByKeyword(keyword, memberId, curPage);
    } catch (ProductNotFoundException e) {
      request.setAttribute("error", e.getMessage());
      return productList();
    }
    request.setAttribute("categories", categories);
    request.setAttribute("productList", productsByKeyword);
    return productList();
  }

  private String productList(HttpServletRequest request) throws Exception {
    List<Category> categories = categoryService.getAllCategory();
    int curPage = Integer.parseInt(request.getParameter("curPage"));
    String sort = request.getParameter("sort");
    ProductListWithPagination productList = service.getProductList(memberId, curPage, sort);
    request.setAttribute("categories", categories);
    request.setAttribute("productList", productList);
    return productList();
  }

  private String productDetail(HttpServletRequest request) throws Exception {
    String productId = request.getParameter("productId");
    ProductDetailWithCategory productDetail =
        service.getProductDetail(memberId, Long.valueOf(productId));
    request.setAttribute("productDetail", productDetail);
    return productDetail();
  }

  private String productList() {
    return Navi.FORWARD_PRODUCT_DETAIL;
  }

  private String productDetail() {
    return Navi.FORWARD_SHOP_DETAIL;
  }
}
