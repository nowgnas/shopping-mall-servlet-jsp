package web.controller;

import app.dto.form.OrderCreateForm;
import app.dto.request.CartOrderCreateDto;
import app.dto.request.OrderCreateDto;
import app.dto.response.MemberDetail;
import app.dto.response.OrderMemberDetail;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.Order;
import app.service.order.OrderServiceImpl;
import app.utils.HttpUtil;
import web.dispatcher.Navi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** Servlet implementation class CustServlet */
@WebServlet({"/order"})
public class OrderServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private final OrderServiceImpl orderService = new OrderServiceImpl();

  public OrderServlet() {
    super();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String view = request.getParameter("view");
    String cmd = request.getParameter("cmd");

    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");
//    Long memberId = loginMember.getId();

    String viewName = Navi.FORWARD_MAIN;

    if (view != null && cmd != null) {
      viewName = build(request, view, cmd, 6L);
    }

    String path = viewName.substring(viewName.indexOf(":") + 1);

    if (viewName.startsWith("forward:")) {
      HttpUtil.forward(request, response, path);
    } else {
      HttpUtil.redirect(response, path);
    }
  }

  private String build(HttpServletRequest request, String view, String cmd, Long memberId) {

    if (view.equals("cart") && cmd.equals("form")) {

      try {
        Long productId = Long.parseLong(request.getParameter("product_id"));
//        OrderMemberDetail createCartOrderForm = orderService.getCreateOrderForm(memberId, productId);
//        request.setAttribute("createCartOrderForm", createCartOrderForm);
        return Navi.FORWARD_ORDER_CART_FORM;
      } catch(Exception e) {
        throw new RuntimeException(e);
      }

    } else if (view.equals("cart") && cmd.equals("create")) {

      Long couponId = Long.parseLong(request.getParameter("coupon_id"));
      String roadName = request.getParameter("road_name");
      String addrDetail = request.getParameter("addr_detail");
      String zipCode = request.getParameter("zip_code");
      Long totalPrice = Long.parseLong(request.getParameter("total_price"));
      if (roadName == null || addrDetail == null || zipCode == null) {
        request.setAttribute("error", "address");
        return "templates/order/orderCartForm.jsp";
      }

      CartOrderCreateDto cartOrderCreateDto =
          new CartOrderCreateDto(
              memberId,
              couponId,
              new CartOrderCreateDto.AddressDto(roadName, addrDetail, zipCode),
              new ArrayList<>(),
              totalPrice);
      try {
        Order order = orderService.createCartOrder(cartOrderCreateDto);
        ProductOrderDetailDto orderDetails =
            orderService.getOrderDetailsForMemberAndOrderId(order.getId(), memberId);
        request.setAttribute("orderDetails", orderDetails);
        return String.format(Navi.REDIRECT_ORDER_DETAIL, order.getId());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    } else if (view.equals("direct") && cmd.equals("form")) {

      try {
//        Long productId = Long.parseLong(request.getParameter("productId"));
//        Long quantity = Long.parseLong(request.getParameter("quantity"));
        Long productId = 1L;
        Long quantity = 1L;
        OrderCreateForm createOrderForm = orderService.getCreateOrderForm(memberId, productId, quantity);
        request.setAttribute("createOrderForm", createOrderForm);
        return Navi.FORWARD_ORDER_FORM;
      } catch(Exception e) {
        throw new RuntimeException(e);
      }

    } else if (view.equals("direct") && cmd.equals("create")) {

      Long couponId = Long.parseLong(request.getParameter("coupon_id"));
      Long productId = Long.parseLong(request.getParameter("product_id"));
      Long quantity = Long.parseLong(request.getParameter("quantity"));
      String roadName = request.getParameter("road_name");
      String addrDetail = request.getParameter("addr_detail");
      String zipCode = request.getParameter("zip_code");
      Long totalPrice = Long.parseLong(request.getParameter("total_price"));
      if (roadName == null || addrDetail == null || zipCode == null) {
        request.setAttribute("error", "address");
        return "templates/order/orderForm.jsp";
      }

      OrderCreateDto orderCreateDto =
          new OrderCreateDto(
              memberId,
              couponId,
              new OrderCreateDto.AddressDto(roadName, addrDetail, zipCode),
              new OrderCreateDto.ProductDto(productId, quantity),
              totalPrice);
      try {
        Order order = orderService.createOrder(orderCreateDto);
        ProductOrderDetailDto productOrderDetail =
            orderService.getOrderDetailsForMemberAndOrderId(order.getId(), memberId);
        request.setAttribute("productOrderDetail", productOrderDetail);
        return String.format(Navi.REDIRECT_ORDER_DETAIL, order.getId());

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    } else if (view.equals("detail") && cmd.equals("get")) {

      try {
        Long orderId = Long.parseLong(request.getParameter("orderId"));
        ProductOrderDetailDto productOrderDetail = orderService.getOrderDetailsForMemberAndOrderId(orderId, memberId);
        request.setAttribute("productOrderDetail", productOrderDetail);
        return Navi.FORWARD_ORDER_DETAIL;
      } catch (Exception e) {
        request.setAttribute("error", "system");
      }

    } else if (view.equals("list") && cmd.equals("get")) {

      try {
        List<ProductOrderDto> productOrders = orderService.getProductOrdersForMemberCurrentYear(memberId);
        request.setAttribute("productOrders", productOrders);
        return Navi.FORWARD_ORDER_LIST;
      } catch (Exception e) {
        request.setAttribute("error", "system");
      }

    } else if (view.equals("detail") && cmd.equals("delete")) {

      try {
        Long orderId = Long.parseLong(request.getParameter("orderId"));
        orderService.cancelOrder(orderId);
        return String.format(Navi.REDIRECT_ORDER_DETAIL, orderId);
      } catch (Exception e) {
        request.setAttribute("error", "system");
      }

    }

    return "index.jsp";
  }
}
