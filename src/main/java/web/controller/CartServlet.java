package web.controller;

import app.dto.cart.AllCartProductInfoDto;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.ErrorCode;
import app.exception.cart.CartNotFoundException;
import app.exception.member.MemberNotFoundException;
import app.exception.product.ProductNotFoundException;
import app.service.cart.CartService;
import app.utils.HttpUtil;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@WebServlet({"/shopping-cart"})
public class CartServlet extends HttpServlet {

  private final CartService cartService;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Long memberId = (Long) request.getSession().getAttribute("memberId");

    try {
      AllCartProductInfoDto cartInfo = cartService.getCartProductListByMember(memberId);
      request.setAttribute("cartInfo", cartInfo);
      request.getRequestDispatcher("cart.jsp").forward(request, response);
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
      Long memberId = (Long) request.getSession().getAttribute("memberId");
      Long productId = Long.parseLong(request.getParameter("productId"));
      ProductAndMemberCompositeKey compositeKey =
          new ProductAndMemberCompositeKey(productId, memberId);

      if ("add".equals(action)) {
        Long quantity = Long.parseLong(request.getParameter("quantity"));
        cartService.putItemIntoCart(compositeKey, quantity);
      } else if ("update".equals(action)) {
        Long updatedQuantity = Long.parseLong(request.getParameter("updatedQuantity"));
        cartService.updateQuantityOfCartProduct(compositeKey, updatedQuantity);
      } else if ("delete".equals(action)) {
        cartService.delete(compositeKey, 1L);
      }
      HttpUtil.forward(request, response, "/shopping-cart");
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
