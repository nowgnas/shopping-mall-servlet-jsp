package web.dispatcher;

public abstract class Navi {

  public static final String FORWARD_DETAIL = "forward:templates/product/shop-details.jsp";
    public static final String FORWARD_PRODUCT_DETAIL = "forward:templates/product/shop.jsp";
  public static String REDIRECT_MAIN = "redirect:index.jsp";
  public static String FORWARD_REGISTER_FORM = "forward:member/registerForm.jsp";
  public static String FORWARD_LOGIN_FORM = "forward:member/loginForm.jsp";
  public static String REDIRECT_LOGIN_FORM = "redirect:member/loginForm.jsp";
  public static String FORWARD_MAIN = "forward:templates/index.jsp";
  public static String REDIRECT_ORDER_DETAIL =
      "redirect:/order.bit?view=detail&cmd=get&order_id=%s";
  public static String FORWARD_ORDER_DETAIL = "forward:templates/order/orderDetail.jsp";
  public static String FORWARD_ORDER_LIST = "forward:templates/order/orderList.jsp";
  public static String FORWARD_ORDER_FORM = "forward:templates/order/orderForm.jsp";
  public static String FORWARD_ORDER_CART_FORM = "forward:templates/order/orderCartForm.jsp";

}
