package web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/rest"})
public class RestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cmd = request.getParameter("cmd");
        Object result = "";

        if(cmd != null){
            result = build(request, cmd);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().print(result);


    }


    private Object build(HttpServletRequest request,
                         String cmd){
        Object result = null;

        return result;
    }
}