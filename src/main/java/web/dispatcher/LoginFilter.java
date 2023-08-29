package web.dispatcher;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

@WebFilter(urlPatterns = "*.bit")
public class LoginFilter implements Filter {

  private Logger work_log = Logger.getLogger("work");

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    request.setCharacterEncoding("UTF-8");
    // 1. request 파라미터를 이용한 요청 필터 작업 수행
    String uri = ((HttpServletRequest) request).getRequestURI();
    work_log.debug("doFilter----" + uri);
    String path = uri.substring(uri.lastIndexOf("/"));
    work_log.debug("doFilter----" + path);
    if (path.equals("/")) {
      ((HttpServletResponse) response).sendRedirect("main.bit");
      return;
    }
    path = path.substring(1, path.lastIndexOf("."));
    String encode = URLEncoder.encode("로그인이필요합니다.", "UTF-8");
    if (path.equals("order") || path.equals("likes") || path.equals("cart")) {
      HttpSession session = ((HttpServletRequest) request).getSession(false);
      if (session == null || session.getAttribute("loginMember") == null) {
        ((HttpServletResponse) response).sendRedirect("main.bit?errorMessage=" + encode);
        return;
      }
    }

    // 2. 체인의 다음 필터 처리
    chain.doFilter(request, response);
  }
}
