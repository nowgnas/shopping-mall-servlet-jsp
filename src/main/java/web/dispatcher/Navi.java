package web.dispatcher;

public abstract class Navi {
  public static final String FORWARD_MAIN = "forward:templates/index.jsp";
  public static final String REDIRECT_MAIN = "redirect:main.bit";
  public static final String FORWARD_REGISTER_FORM = "forward:templates/member/registerForm.jsp";
  public static final String FORWARD_LOGIN_FORM = "forward:templates/member/loginForm.jsp";
  public static final String REDIRECT_ORDER_DETAIL =
      "redirect:order.bit?view=detail&cmd=get&orderId=%s";
  public static final String FORWARD_SHOP_DETAIL = "forward:templates/product/shop-details.jsp";
  public static final String REDIRECT_SHOP_DETAIL = "";
  public static final String FORWARD_CART_FORM = "";
  public static final String REDIRECT_CART_FORM = "";
  public static final String FORWARD_ORDER_DETAIL = "forward:templates/order/orderDetail.jsp";
  public static final String FORWARD_ORDER_LIST = "forward:templates/order/orderList.jsp";
  public static final String REDIRECT_ORDER_LIST = "redirect:order.bit?view=list&cmd=get";
  public static final String FORWARD_ORDER_FORM = "forward:templates/order/orderForm.jsp";
  public static final String FORWARD_ORDER_CART_FORM = "forward:templates/order/orderCartForm.jsp";
  public static final String FORWARD_LIKES_LIST = "forward:templates/likes/likesList.jsp";
  public static final String REDIRECT_LIKES_LIST = "";
}
