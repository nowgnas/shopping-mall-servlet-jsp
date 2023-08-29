package web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControllerFrame {
  String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
