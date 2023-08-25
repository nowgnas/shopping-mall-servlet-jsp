package web.dispatcher;

import app.utils.HttpUtil;
import org.apache.log4j.Logger;
import web.ControllerFrame;
import web.RestControllerFrame;
import web.controller.*;
import web.restController.LikesRestController;
import web.restController.MemberRestController;

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
  private Map<String, RestControllerFrame> restControllerMapper = new HashMap<>();
  private Logger work_log = Logger.getLogger("work");

  public DispatcherServlet() {
    super();
    controllerMapper.put("main", new MainController());
    controllerMapper.put("member", new MemberController());
    controllerMapper.put("order", new OrderController());
    controllerMapper.put("product", new ProductController());
    controllerMapper.put("likes", new LikesController());
    restControllerMapper.put("member-rest", new MemberRestController());
    restControllerMapper.put("likes", new LikesRestController());
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String uri = request.getRequestURI();
    request.setCharacterEncoding("UTF-8");
    String path = uri.substring(uri.lastIndexOf("/"));
    work_log.debug(path);
    path = path.substring(1, path.lastIndexOf("."));
    work_log.debug(path);
    String next = Navi.REDIRECT_MAIN;
    try {
      if (controllerMapper.containsKey(path)) {
        ControllerFrame controller = controllerMapper.get(path);
        next = controller.execute(request, response);

        String resultPath = next.substring(next.indexOf(":") + 1);

        if (next.startsWith("forward:")) {
          HttpUtil.forward(request, response, resultPath);
        } else {
          HttpUtil.redirect(response, resultPath);
        }
      }
      if (restControllerMapper.containsKey(path)) {
        RestControllerFrame restController = restControllerMapper.get(path);
        Object result = restController.execute(request, response);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(result);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
