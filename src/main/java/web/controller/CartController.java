package web.controller;

import app.dao.cart.CartDao;
import app.dao.cart.CartDaoFrame;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dto.cart.AllCartProductInfoDtoWithPagination;
import app.dto.member.response.MemberDetail;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.cart.CartNotFoundException;
import app.exception.product.ProductNotFoundException;
import app.service.cart.CartService;
import app.service.cart.CartServiceImpl;
import app.service.cart.DeleteCartWhenRestOfQuantityUnder0;
import app.service.cart.UpdateCartService;
import app.service.cart.UpdateCartServiceImpl;
import app.service.checker.CartExistCheckerService;
import app.service.checker.EntityExistCheckerService;
import app.service.checker.MemberExistCheckerService;
import app.service.checker.ProductExistCheckerService;
import app.service.product.StockCheckerService;
import app.service.product.StockCheckerServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import web.ControllerFrame;
import web.dispatcher.Navi;

@NoArgsConstructor
public class CartController implements ControllerFrame {

  private final CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame = new CartDao();
  private final MemberDaoFrame<Long, Member> memberDao = new MemberDao();
  private final EntityExistCheckerService<Long, Member> memberExistCheckerService = new MemberExistCheckerService(
      memberDao);
  private final EntityExistCheckerService<Long, Product> productExistCheckerService = new ProductExistCheckerService();
  private final EntityExistCheckerService<ProductAndMemberCompositeKey, Cart> cartExistCheckerService = new CartExistCheckerService();
  private final UpdateCartService updateCartService = new UpdateCartServiceImpl(cartDaoFrame,
      new DeleteCartWhenRestOfQuantityUnder0(cartDaoFrame));
  private final StockCheckerService stockCheckerService = new StockCheckerServiceImpl();
  private final CartService cartService = new CartServiceImpl(cartDaoFrame, memberDao,
      memberExistCheckerService, productExistCheckerService, cartExistCheckerService,
      stockCheckerService, updateCartService);
  private Long memberId;


  public String addProductInCart(HttpServletRequest request) throws Exception {
    try {
      Long productId = Long.parseLong(request.getParameter("productId"));
      Long quantity = Long.parseLong(request.getParameter("quantity"));
      cartService.putItemIntoCart(new ProductAndMemberCompositeKey(memberId, productId), quantity);
      return Navi.FORWARD_CART_FORM;
  } catch (ProductNotFoundException | CartNotFoundException  e) {
      return Navi.REDIRECT_CART_FORM + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.REDIRECT_MAIN;
  }

  public String deleteProductInCart(HttpServletRequest request) {
     try {
      Long productId = Long.parseLong(request.getParameter("productId"));
        cartService.delete(new ProductAndMemberCompositeKey(memberId, productId));
      return Navi.FORWARD_CART_FORM;
  } catch (ProductNotFoundException | CartNotFoundException  e) {
      return Navi.REDIRECT_CART_FORM + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.REDIRECT_MAIN;
  }

  public String updateProductInCart(HttpServletRequest request) {
 try {
      Long productId = Long.parseLong(request.getParameter("productId"));
      Long quantity = Long.parseLong(request.getParameter("quantity"));
      cartService.updateQuantityOfCartProduct(new ProductAndMemberCompositeKey(memberId, productId), quantity);
      return Navi.FORWARD_CART_FORM;
  } catch (ProductNotFoundException | CartNotFoundException  e) {
      return Navi.REDIRECT_CART_FORM + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.REDIRECT_MAIN;
  }

  public String getCartList(HttpServletRequest request) {
    try {
      AllCartProductInfoDtoWithPagination cartInfo = cartService.getCartProductListByMemberPagination(
          memberId);
      request.setAttribute("productList",
          cartInfo.getCartProductInfoDto().getCartProductDtoList());
      request.setAttribute("totalPrice", cartInfo.getCartProductInfoDto().getTotalPrice());
      request.setAttribute("pagination", cartInfo.getPaging());
      return Navi.FORWARD_CART_FORM;
    }
     catch (ProductNotFoundException | CartNotFoundException  e) {
      return Navi.REDIRECT_CART_FORM + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.REDIRECT_MAIN;
  }

  private String build(HttpServletRequest request, HttpServletResponse response, String action)
      throws Exception {
    switch (action) {
      case "get":
        return getCartList(request);
      case "add":
        return addProductInCart(request);
      case "delete":
        return deleteProductInCart(request);
      case "update":
        return updateProductInCart(request);
      default:
        return Navi.FORWARD_MAIN;
    }

  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");

    String next = Navi.REDIRECT_MAIN;
    String view = request.getParameter("action");
    if (view != null && loginMember !=null) {
      memberId = loginMember.getId();
      next = build(request, response, view);
    }
    return next;
  }
}
