package web.restController;

import app.dto.order.request.OrderCartCreateDto;
import app.dto.order.request.OrderCreateDto;
import app.dto.order.response.ProductOrderDetailDto;
import app.dto.request.MemberRegisterDto;
import app.dto.response.MemberDetail;
import app.entity.Order;
import app.exception.DomainException;
import app.exception.member.RegisterException;
import app.service.member.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import app.service.order.OrderService;
import web.RestControllerFrame;
import web.controller.validation.MemberValidation;
import web.dispatcher.Navi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class OrderRestController implements RestControllerFrame {
  private OrderService orderService;
  private Long memberId;

  public OrderRestController() {
    super();
    orderService = new OrderService();
  }

  @Override
  public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    String cmd = request.getParameter("cmd");

    HttpSession session = request.getSession();
    MemberDetail loginMember = (MemberDetail) session.getAttribute("loginMember");

    Object result = null;

    if (loginMember != null && cmd != null) {
      memberId = loginMember.getId();
      result = build(request, response, cmd);
    }

    return result;
  }

  private Object build(HttpServletRequest request, HttpServletResponse response, String cmd)
      throws Exception {
    Object result = null;
    switch (cmd) {
      case "orderCreate":
        return createOrder(request, response);
      case "orderCartCreate":
        return createCartOrder(request, response);
    }
    return result;
  }

  // TODO: 상품 주문
  private Object createOrder(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    Long couponId =
        request.getParameter("couponId").equals("0")
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
    return order.getId();
  }

  // TODO: 장바구니 상품 주문
  private Object createCartOrder(HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    Long couponId =
        request.getParameter("couponId").equals("0")
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
    return order.getId();
  }
}
