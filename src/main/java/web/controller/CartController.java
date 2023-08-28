package web.controller;

import app.dao.cart.CartDao;
import app.dao.cart.CartDaoFrame;
import app.dao.member.MemberDao;
import app.dao.member.MemberDaoFrame;
import app.dto.cart.AllCartProductInfoDtoWithPagination;
import app.entity.Cart;
import app.entity.Member;
import app.entity.Product;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.ErrorCode;
import app.exception.cart.CartNotFoundException;
import app.exception.member.MemberNotFoundException;
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
import app.utils.HttpUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@WebServlet({"/cart"})
public class CartController extends HttpServlet {

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


  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
//    Long memberId = (Long) request.getSession().getAttribute("memberId");
    log("once is completed");
    Long memberId = 1L;

    try {

      AllCartProductInfoDtoWithPagination cartInfo = cartService.getCartProductListByMemberPagination(
          memberId);
      request.setAttribute("productList",
          cartInfo.getCartProductInfoDto().getCartProductDtoList());
      request.setAttribute("totalPrice", cartInfo.getCartProductInfoDto().getTotalPrice());
      request.setAttribute("pagination", cartInfo.getPaging());
      request.getRequestDispatcher("templates/cart/cart.jsp").forward(request, response);
    } catch (MemberNotFoundException e) {
      throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getParameter("action");

    try {
      Long memberId = 1L;
//      Long memberId = (Long) request.getSession().getAttribute("memberId");
      Long productId = Long.parseLong(request.getParameter("productId"));

      ProductAndMemberCompositeKey compositeKey = new ProductAndMemberCompositeKey(productId,
          memberId);

      if ("add".equals(action)) {
        Long quantity = Long.parseLong(request.getParameter("quantity"));
        cartService.putItemIntoCart(compositeKey, quantity);
      } else if ("update".equals(action)) {
        Long updatedQuantity = Long.parseLong(request.getParameter("updatedQuantity"));
        cartService.updateQuantityOfCartProduct(compositeKey, updatedQuantity);
      } else if ("delete".equals(action)) {
        cartService.delete(compositeKey);
      }
      HttpUtil.redirect(response, "/cart");
    } catch (MemberNotFoundException e) {
      throw new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND);
    } catch (ProductNotFoundException e) {
      throw new ProductNotFoundException(ErrorCode.ITEM_NOT_FOUND);
    } catch (CartNotFoundException e) {
      throw new CartNotFoundException(ErrorCode.CART_IS_NOT_EXISTED);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

}
