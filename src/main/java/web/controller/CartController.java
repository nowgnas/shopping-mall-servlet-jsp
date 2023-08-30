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
import app.exception.cart.OutOfStockException;
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
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import web.ControllerFrame;
import web.dispatcher.Navi;

@Log
@AllArgsConstructor
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


  public String addProductInCart(HttpServletRequest request) throws Exception {
    try {
      MemberDetail memberDetail = (MemberDetail) request.getSession().getAttribute("loginMember");
      Long productId = Long.parseLong(request.getParameter("productId"));
      Long quantity = Long.parseLong(request.getParameter("quantity"));
      cartService.putItemIntoCart(new ProductAndMemberCompositeKey(productId, memberDetail.getId()),
          quantity);
      return Navi.FORWARD_CART_FORM;

    }
    catch (ProductNotFoundException | CartNotFoundException  e) {
      return Navi.REDIRECT_CART_FORM + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.FORWARD_CART_FORM;
  }

  public String deleteProductInCart(HttpServletRequest request, HttpServletResponse response) {
    try {

      MemberDetail memberDetail = (MemberDetail) request.getSession().getAttribute("loginMember");
      Long productId = Long.parseLong(request.getParameter("productId"));
      response.setHeader("X-Content-Type-Options", "nosniff");
      cartService.delete(new ProductAndMemberCompositeKey(productId, memberDetail.getId()));
      return Navi.FORWARD_CART_FORM;
    } catch (ProductNotFoundException | CartNotFoundException e) {
      return Navi.FORWARD_CART_FORM;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.FORWARD_CART_FORM;
  }

  public String updateProductInCart(HttpServletRequest request, HttpServletResponse response) {
    try {
      MemberDetail memberDetail = (MemberDetail) request.getSession().getAttribute("loginMember");
      Long productId = Long.parseLong(request.getParameter("productId"));
      Long quantity = Long.parseLong(request.getParameter("updatedQuantity"));
      cartService.updateQuantityOfCartProduct(
          new ProductAndMemberCompositeKey(productId, memberDetail.getId()),
          quantity);
      response.setHeader("X-Content-Type-Options", "nosniff");
      return Navi.FORWARD_CART_FORM;
    } catch (ProductNotFoundException | CartNotFoundException e) {
      return Navi.REDIRECT_CART_FORM + String.format("?errorMessage=%s", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.FORWARD_CART_FORM;
  }

  public String getCartList(HttpServletRequest request, HttpServletResponse response) {
    try {
      MemberDetail memberDetail = (MemberDetail) request.getSession().getAttribute("loginMember");
      AllCartProductInfoDtoWithPagination cartInfo = cartService.getCartProductListByMemberPagination(
          memberDetail.getId());
      request.setAttribute("productList",
          cartInfo.getCartProductInfoDto().getCartProductDtoList());
      request.setAttribute("totalPrice", cartInfo.getCartProductInfoDto().getTotalPrice());
      request.setAttribute("pagination", cartInfo.getPaging());
      response.setHeader("X-Content-Type-Options", "nosniff");
      return Navi.FORWARD_CART_FORM;
    } catch (ProductNotFoundException | CartNotFoundException  e) {
      log.info("카트에 상품이 존재하지 않습니다");
      return Navi.FORWARD_CART_FORM;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Navi.FORWARD_CART_FORM;
  }

  private String build(HttpServletRequest request, HttpServletResponse response, String action)
      throws Exception {
    switch (action) {
      case "get":
        return getCartList(request, response);
      case "add":
        return addProductInCart(request);
      case "delete":
        return deleteProductInCart(request, response);
      case "update":
        return updateProductInCart(request, response);
      default:
        return Navi.FORWARD_CART_FORM;
    }

  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String view = request.getParameter("action");
    log.info(request.getSession().toString());
    return build(request, response, view);
  }
}
