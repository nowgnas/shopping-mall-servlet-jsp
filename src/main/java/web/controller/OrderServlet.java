package web.controller;

import app.dto.form.OrderCreateForm;
import app.dto.request.CartOrderCreateDto;
import app.dto.request.OrderCreateDto;
import app.dto.response.MemberDetail;
import app.dto.response.ProductOrderDetailDto;
import app.dto.response.ProductOrderDto;
import app.entity.Order;
import app.service.order.OrderServiceImpl;
import app.utils.HttpUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.dispatcher.Navi;

/** Servlet implementation class CustServlet */
@WebServlet({"/order"})
public class OrderServlet extends HttpServlet {

  private Long memberId;
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
    memberId = 6L;
    /* 로그인이 되어있지 않다면 메인으로 리다이렉트 */
    //    if(memberId == null) {
    //
    //    }

    String viewName = Navi.FORWARD_MAIN;

    if (view != null && cmd != null) {
      viewName = build(request, response, view, cmd);
    }

    String path = viewName.substring(viewName.indexOf(":") + 1);

    if (viewName.startsWith("forward:")) {
      HttpUtil.forward(request, response, path);
    } else {
      HttpUtil.redirect(response, path);
    }
  }

  private String build(
      HttpServletRequest request, HttpServletResponse response, String view, String cmd) {
    if (view.equals("direct") && cmd.equals("form")) {
      return getCreateOrderForm(request, response);
    } else if (view.equals("direct") && cmd.equals("create")) {
      return createOrder(request, response);
    } else if (view.equals("cart") && cmd.equals("form")) {
      return getCreateCartOrderForm(request, response);
    } else if (view.equals("cart") && cmd.equals("create")) {
      return createCartOrder(request, response);
    } else if (view.equals("detail") && cmd.equals("get")) {
      return getProductOrderDetail(request, response);
    } else if (view.equals("list") && cmd.equals("get")) {
      return getProductOrders(request, response);
    } else if (view.equals("detail") && cmd.equals("delete")) {
      return deleteOrder(request, response);
    }

    return Navi.FORWARD_MAIN;
  }

  // TODO: 상품 주문 폼
  private String getCreateOrderForm(HttpServletRequest request, HttpServletResponse response) {
    try {
      //      Long productId = Long.parseLong(request.getParameter("productId"));
      //      Long quantity = Long.parseLong(request.getParameter("quantity"));
      Long productId = 1L;
      Long quantity = 1L;

      OrderCreateForm createOrderForm = orderService.getCreateOrderForm(memberId, productId);
      request.setAttribute("memberName", createOrderForm.getMemberName());
      request.setAttribute("defaultAddress", createOrderForm.getDefaultAddress());
      request.setAttribute("product", createOrderForm.getProduct());
      request.setAttribute("productQuantity", quantity);
      request.setAttribute("coupons", createOrderForm.getCoupons());

      return Navi.FORWARD_ORDER_FORM;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // TODO: 상품 주문
  private String createOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long couponId =
          Long.parseLong(request.getParameter("couponId")) == 0
              ? null
              : Long.parseLong(request.getParameter("couponId"));
      Long productId = Long.parseLong(request.getParameter("productId"));
      Long price = Long.parseLong(request.getParameter("productPrice"));
      Long quantity = Long.parseLong(request.getParameter("productQuantity"));
      String roadName = request.getParameter("roadName");
      String addrDetail = request.getParameter("addrDetail");
      String zipCode = request.getParameter("zipCode");
      Long totalPrice = Long.parseLong(request.getParameter("totalPrice"));

      OrderCreateDto orderCreateDto =
          OrderCreateDto.builder()
              .memberId(memberId)
              .couponId(couponId)
              .roadName(roadName)
              .addrDetail(addrDetail)
              .zipCode(zipCode)
              .productId(productId)
              .price(price)
              .quantity(quantity)
              .totalPrice(totalPrice)
              .build();
      Order order = orderService.createOrder(orderCreateDto);
      ProductOrderDetailDto productOrderDetail =
          orderService.getOrderDetailsForMemberAndOrderId(order.getId(), memberId);
      request.setAttribute("productOrderDetail", productOrderDetail);
      return String.format(Navi.REDIRECT_ORDER_DETAIL, order.getId());
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  // TODO: 장바구니 상품 주문 폼
  private String getCreateCartOrderForm(HttpServletRequest request, HttpServletResponse response) {
    try {
      /* 회원의 기본 주소지, 보유한 쿠폰 정보들을 가져옴 */

      /* 장바구니에 담긴 상품 정보들을 가져옴 */

      return Navi.FORWARD_ORDER_CART_FORM;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  // TODO: 장바구니 상품 주문
  private String createCartOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long couponId = Long.parseLong(request.getParameter("couponId"));
      String roadName = request.getParameter("roadName");
      String addrDetail = request.getParameter("addrDetail");
      String zipCode = request.getParameter("zipCode");
      Long totalPrice = Long.parseLong(request.getParameter("totalPrice"));

      CartOrderCreateDto cartOrderCreateDto =
          new CartOrderCreateDto(
              memberId,
              couponId,
              new CartOrderCreateDto.AddressDto(roadName, addrDetail, zipCode),
              new ArrayList<>(),
              totalPrice);

      Order order = orderService.createCartOrder(cartOrderCreateDto);
      ProductOrderDetailDto orderDetails =
          orderService.getOrderDetailsForMemberAndOrderId(order.getId(), memberId);
      request.setAttribute("orderDetails", orderDetails);
      return String.format(Navi.REDIRECT_ORDER_DETAIL, order.getId());
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  private String deleteOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long orderId = Long.parseLong(request.getParameter("orderId"));
      orderService.cancelOrder(orderId);
      return String.format(Navi.REDIRECT_ORDER_DETAIL, orderId);
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  private String getProductOrders(HttpServletRequest request, HttpServletResponse response) {
    try {
      List<ProductOrderDto> productOrders =
          orderService.getProductOrdersForMemberCurrentYear(memberId);
      request.setAttribute("productOrders", productOrders);
      return Navi.FORWARD_ORDER_LIST;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  private String getProductOrderDetail(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long orderId = Long.parseLong(request.getParameter("orderId"));
      ProductOrderDetailDto productOrderDetail =
          orderService.getOrderDetailsForMemberAndOrderId(orderId, memberId);
      request.setAttribute("productOrderDetail", productOrderDetail);
      return Navi.FORWARD_ORDER_DETAIL;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }
}
