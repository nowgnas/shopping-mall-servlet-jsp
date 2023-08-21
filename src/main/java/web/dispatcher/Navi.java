package web.dispatcher;

public abstract class Navi {

  public static final String REDIRECT_DETAIL = "redirect:templates/product/detail.jsp";
  public static String REDIRECT_MAIN = "redirect:index.jsp";
  public static String FORWARD_REGISTER_FORM = "forward:member/registerForm.jsp";
  public static String FORWARD_LOGIN_FORM = "forward:member/loginForm.jsp";
  public static String REDIRECT_LOGIN_FORM = "redirect:member/loginForm.jsp";
}
