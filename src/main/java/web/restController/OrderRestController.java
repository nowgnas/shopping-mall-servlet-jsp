package web.restController;

import app.dto.order.request.OrderCartCreateDto;
import app.dto.order.request.OrderCreateDto;
import app.dto.member.response.MemberDetail;
import app.entity.Order;
import app.exception.DomainException;
import app.service.order.OrderService;
import web.RestControllerFrame;
import web.dispatcher.Navi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
      case "orderDelete":
        return deleteOrder(request, response);
    }
    return result;
  }

  // TODO: 상품 주문
  private Object createOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
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
      if(productId == 0L || quantity == 0L || roadName.isBlank() || addrDetail.isBlank() || zipCode.isBlank()) {
        throw new RuntimeException("올바르지 않은 요청 형식입니다.");
      }

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
    } catch (DomainException e) {
      response.setStatus(e.getStatusCode());
      return e.getMessage();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return "시스템 에러";
    }
  }

  // TODO: 장바구니 상품 주문
  private Object createCartOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long couponId =
              request.getParameter("couponId").equals("0")
                      ? null
                      : Long.parseLong(request.getParameter("couponId"));
      String roadName = request.getParameter("roadName");
      String addrDetail = request.getParameter("addrDetail");
      String zipCode = request.getParameter("zipCode");
      Long totalPrice = Long.parseLong(request.getParameter("totalPrice"));
      if(roadName.isBlank() || addrDetail.isBlank() || zipCode.isBlank()) {
        throw new RuntimeException("올바르지 않은 요청 형식입니다.");
      }

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
    } catch (DomainException e) {
      response.setStatus(e.getStatusCode());
      return e.getMessage();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return "시스템 에러";
    }
  }

  private Object deleteOrder(HttpServletRequest request, HttpServletResponse response) {
    try {
      Long orderId = Long.parseLong(request.getParameter("orderId"));
      orderService.cancelOrder(orderId);
      return orderId;
    } catch (DomainException e) {
      response.setStatus(e.getStatusCode());
      return e.getMessage();
    } catch (RuntimeException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return "시스템 에러";
    }
  }
}
