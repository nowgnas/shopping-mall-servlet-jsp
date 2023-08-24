package web.dispatcher;

import app.utils.HttpUtil;
import org.apache.log4j.Logger;
import web.ControllerFrame;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet({"/DispatcherServlet", "/web/dispatcher", "*.bit"})
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Map<String, ControllerFrame> controllerMapper = new HashMap<>();
  private Logger work_log = Logger.getLogger("work");

  public DispatcherServlet() {
    super();
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String uri = request.getRequestURI();
    request.setCharacterEncoding("UTF-8");
    String path = uri.substring(uri.lastIndexOf("/"));
    work_log.debug(path);
    path = path.substring(1, path.lastIndexOf("."));
    work_log.debug(path);
    String next = "main.jsp";
    if (path != null) {
      next = path;
    }

    String resultPath = next.substring(next.indexOf(":") + 1);

    if (next.startsWith("forward:")) {
      HttpUtil.forward(request, response, resultPath);
    } else {
      HttpUtil.redirect(response, resultPath);
    }


//    RequestDispatcher rd = request.getRequestDispatcher(next);
//    rd.forward(request, response);
  }
}
