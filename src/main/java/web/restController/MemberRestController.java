package web.restController;

import app.service.member.MemberService;
import web.RestControllerFrame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberRestController implements RestControllerFrame {
    private MemberService memberService;

    public MemberRestController() {
        super();
        memberService = new MemberService();
    }

    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String cmd = request.getParameter("cmd");
        Object result = "";

        if (cmd != null) {
            result = build(request, cmd);
        }
        return result;
    }

    private Object build(HttpServletRequest request, String cmd) {
        Object result = null;
        switch (cmd) {
            case "loginCheck":
                return loginCheck(request);
        }
        return result;
    }

    private Object loginCheck(HttpServletRequest request) {
        String email = request.getParameter("email");
        return memberService.isDuplicatedEmail(email);
    }
}
