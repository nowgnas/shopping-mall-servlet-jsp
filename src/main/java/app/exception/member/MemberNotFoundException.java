package app.exception.member;

import app.exception.ErrorCode;

public class MemberNotFoundException extends RuntimeException {

  public MemberNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
