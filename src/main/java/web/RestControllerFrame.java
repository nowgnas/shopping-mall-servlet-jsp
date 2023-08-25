package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RestControllerFrame {
  public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
