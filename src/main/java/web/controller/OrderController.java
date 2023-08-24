package web.controller;

import app.dto.order.form.OrderCartCreateForm;
import app.dto.order.form.OrderCreateForm;
import app.dto.order.request.OrderCartCreateDto;
import app.dto.order.request.OrderCreateDto;
import app.dto.response.MemberDetail;
import app.dto.order.response.ProductOrderDetailDto;
import app.dto.order.response.ProductOrderDto;
import app.entity.Order;
import app.exception.DomainException;
import app.exception.order.OrderProductNotEnoughStockQuantityException;
import app.service.order.OrderService;
import web.ControllerFrame;
import web.dispatcher.Navi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OrderController implements ControllerFrame {

  private final OrderService orderService = new OrderService();
  private Long memberId;

  public OrderController() {
    super();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String view = request.getParameter("view");
    String cmd = request.getParameter("cmd");

    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");

    String viewName = Navi.REDIRECT_MAIN;

    if (loginMember == null) {
      viewName = Navi.REDIRECT_MAIN;
    }

    if (loginMember != null && view != null && cmd != null) {
      memberId = loginMember.getId();
      viewName = build(request, response, view, cmd);
    }

    return viewName;
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

    return Navi.REDIRECT_MAIN;
  }

  // TODO: 상품 주문 폼
  private String getCreateOrderForm(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long productId = Long.parseLong(request.getParameter("productId"));
      Long quantity = Long.parseLong(request.getParameter("quantity"));

      OrderCreateForm createOrderForm = orderService.getCreateOrderForm(memberId, productId);
      if (createOrderForm.getProduct().getQuantity() - quantity < 0) {
        throw new OrderProductNotEnoughStockQuantityException();
      }
      request.setAttribute("memberName", createOrderForm.getMemberName());
      request.setAttribute("defaultAddress", createOrderForm.getDefaultAddress());
      request.setAttribute("product", createOrderForm.getProduct());
      request.setAttribute("productQuantity", quantity);
      request.setAttribute("coupons", createOrderForm.getCoupons());

      return Navi.FORWARD_ORDER_FORM;
    } catch (DomainException e) {
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
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
    } catch (DomainException e) {
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  // TODO: 장바구니 상품 주문 폼
  private String getCreateCartOrderForm(HttpServletRequest request, HttpServletResponse response) {
    try {

      OrderCartCreateForm createCartOrderForm = orderService.getCreateCartOrderForm(memberId);
      request.setAttribute("memberName", createCartOrderForm.getMemberName());
      request.setAttribute("defaultAddress", createCartOrderForm.getDefaultAddress());
      request.setAttribute("products", createCartOrderForm.getProducts());
      request.setAttribute("coupons", createCartOrderForm.getCoupons());

      return Navi.FORWARD_ORDER_CART_FORM;
    } catch (DomainException e) {
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  // TODO: 장바구니 상품 주문
  private String createCartOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long couponId =
          Long.parseLong(request.getParameter("couponId")) == 0
              ? null
              : Long.parseLong(request.getParameter("couponId"));
      String roadName = request.getParameter("roadName");
      String addrDetail = request.getParameter("addrDetail");
      String zipCode = request.getParameter("zipCode");
      Long totalPrice = Long.parseLong(request.getParameter("totalPrice"));

      OrderCartCreateDto orderCartCreateDto =
          OrderCartCreateDto.builder()
              .memberId(memberId)
              .couponId(couponId)
              .roadName(roadName)
              .addrDetail(addrDetail)
              .zipCode(zipCode)
              .totalPrice(totalPrice)
              .build();

      Order order = orderService.createCartOrder(orderCartCreateDto);
      ProductOrderDetailDto orderDetails =
          orderService.getOrderDetailsForMemberAndOrderId(order.getId(), memberId);
      request.setAttribute("orderDetails", orderDetails);
      return String.format(Navi.REDIRECT_ORDER_DETAIL, order.getId());
    } catch (DomainException e) {
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  private String deleteOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long orderId = Long.parseLong(request.getParameter("orderId"));
      orderService.cancelOrder(orderId);
      return String.format(Navi.REDIRECT_ORDER_DETAIL, orderId);
    } catch (DomainException e) {
      return Navi.FORWARD_MAIN;
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
    } catch (DomainException e) {
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }

  private String getProductOrderDetail(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long orderId = Long.parseLong(request.getParameter("orderId"));
      ProductOrderDetailDto productOrderDetail =
          orderService.getOrderDetailsForMemberAndOrderId(orderId, memberId);
      request.setAttribute("products", productOrderDetail.getProducts());
      request.setAttribute("payment", productOrderDetail.getPayment());
      request.setAttribute("delivery", productOrderDetail.getDelivery());
      request.setAttribute("productOrderDetail", productOrderDetail);
      return Navi.FORWARD_ORDER_DETAIL;
    } catch (DomainException e) {
      return Navi.FORWARD_MAIN;
    } catch (Exception e) {
      return Navi.REDIRECT_MAIN;
    }
  }
}
