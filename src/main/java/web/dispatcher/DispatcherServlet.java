package web.dispatcher;

import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({ "/DispatcherServlet", "/web/dispatcher", "*.bit"})
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger work_log = Logger.getLogger("work");
	
	public DispatcherServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		request.setCharacterEncoding("UTF-8");
		String path = uri.substring(uri.lastIndexOf("/"));
		work_log.debug(path);
		path = path.substring(1, path.lastIndexOf("."));
		work_log.debug(path);
		String next = "main.jsp";
		if(path != null) {
			next = path;		
		}
		RequestDispatcher rd = request.getRequestDispatcher(next);
		rd.forward(request, response);
	}
}





