package app.exception.member;

import app.exception.CustomException;
import app.exception.ErrorCode;

public class MemberNotFoundException extends CustomException {

  public MemberNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
