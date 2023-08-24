package web.controller;

import app.dto.request.LoginDto;
import app.dto.request.MemberRegisterDto;
import app.dto.response.MemberDetail;
import app.exception.CustomException;
import app.exception.ErrorCode;
import app.service.member.MemberService;
import web.ControllerFrame;
import web.controller.validation.MemberValidation;
import web.dispatcher.Navi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberController implements ControllerFrame {
    
    private final MemberService memberService;

    public MemberController() {
        super();
        memberService = new MemberService();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String next = Navi.REDIRECT_MAIN;
        String view = request.getParameter("view");
        if (view != null) {
            next = build(request, view);
        }
        return next;
    }

    private String build(HttpServletRequest request, String view) {
        String path = Navi.FORWARD_MAIN;
        switch (view) {
            case "registerForm":
                return registerForm();
            case "register":
                return register(request);
            case "loginForm":
                return loginForm();
            case "login":
                return login(request);
            case "logout":
                return logout(request);
        }
        return path;
    }

    private String registerForm() {
        return Navi.FORWARD_REGISTER_FORM;
    }

    private String register(HttpServletRequest request) {

        String email = request.getParameter("registerEmail");
        String password = request.getParameter("registerPassword");
        String name = request.getParameter("registerName");

        MemberRegisterDto dto = new MemberRegisterDto(email, password, name);

        if (!registerValidationCheck(dto)) {
            throw new CustomException(ErrorCode.REGISTER_FAIL);
        }

        memberService.register(dto);

        return Navi.REDIRECT_MAIN;
    }

    private String loginForm() {
        return Navi.FORWARD_LOGIN_FORM;
    }

    private String login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginDto loginDto = new LoginDto(email, password);
        MemberDetail loginMember = memberService.login(loginDto);

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return Navi.REDIRECT_MAIN;
    }

    private String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("center", "index");
        return Navi.REDIRECT_MAIN;
    }

    private boolean registerValidationCheck(MemberRegisterDto dto) {
        if (!MemberValidation.isValidEmail(dto.getEmail())) {
            return false;
        }
        if (!MemberValidation.isValidPassword(dto.getPassword())) {
            return false;
        }
        if (!MemberValidation.isValidName(dto.getName())) {
            return false;
        }
        return true;
    }


}
