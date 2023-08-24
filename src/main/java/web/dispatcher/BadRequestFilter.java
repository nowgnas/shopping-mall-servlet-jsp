package web.dispatcher;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class BadRequestFilter implements Filter {
  private Logger work_log = Logger.getLogger("work");

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // 필터 초기화 작업
  }

  @Override
  public void destroy() {
    // 주로 필터가 사용한 자원 반납
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 1. request 파라미터를 이용한 요청 필터 작업 수행
    request.setCharacterEncoding("UTF-8");
    String uri = ((HttpServletRequest) request).getRequestURI();
    work_log.debug("doFilter----" + uri);

    String path = uri.substring(uri.lastIndexOf("/"));
    work_log.debug("doFilter----" + path);
    // 사용자의 요청이 루트라면 main.bit로 리다이렉트
    if (path.equals("/")) {
      ((HttpServletResponse) response).sendRedirect("main.bit");
      return;
    }

    // 2. 체인의 다음 필터 처리
    chain.doFilter(request, response);

    // 3. response 를 이용한 요청 필터링 작업 수행

  }
}
