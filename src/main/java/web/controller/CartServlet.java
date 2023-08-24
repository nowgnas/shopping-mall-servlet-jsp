package web.controller;

import app.dto.cart.AllCartProductInfoDto;
import app.service.cart.CartService;
import app.service.cart.CartServiceImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
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
        } catch (Exception e) {
            // Handle exceptions
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Handle cart-related actions like adding/removing products, updating quantities, etc.
        // You will need to implement the necessary logic here
    }

}
