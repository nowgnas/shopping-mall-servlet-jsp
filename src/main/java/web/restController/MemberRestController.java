package web.restController;

import app.dto.request.MemberRegisterDto;
import app.exception.member.RegisterException;
import app.service.member.MemberService;
import web.RestControllerFrame;
import web.controller.validation.MemberValidation;

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
      case "register":
        return register(request);
    }
    return result;
  }

  private Object loginCheck(HttpServletRequest request) {
    String email = request.getParameter("email");
    return memberService.isDuplicatedEmail(email);
  }

  private Boolean register(HttpServletRequest request) {

    String email = request.getParameter("registerEmail");
    String password = request.getParameter("registerPassword");
    String name = request.getParameter("registerName");

    MemberRegisterDto dto = new MemberRegisterDto(email, password, name);

    if (!registerValidationCheck(dto)) {
      throw new RegisterException();
    }

    return memberService.register(dto);
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
