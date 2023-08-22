package app.utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HttpUtil {

  public static void forward(
      HttpServletRequest request, HttpServletResponse response, String path) {
    try {
      RequestDispatcher dispatcher = request.getRequestDispatcher(path);
      dispatcher.forward(request, response);
    } catch (Exception e) {
      throw new RuntimeException("forward 예외 : " + e);
    }
  }

  public static void redirect(HttpServletResponse response, String path) {
    try {
      response.sendRedirect(path);
    } catch (Exception e) {
      throw new RuntimeException("redirect 예외 : " + e);
    }
  }
}
