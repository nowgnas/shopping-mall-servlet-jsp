package app.exception.member;

import app.exception.DomainException;

import javax.servlet.http.HttpServletResponse;

public class DuplicatedEmailException extends DomainException {

  private static final String message = "중복 된 이메일 입니다.";

  public DuplicatedEmailException() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return HttpServletResponse.SC_BAD_REQUEST;
  }
}
