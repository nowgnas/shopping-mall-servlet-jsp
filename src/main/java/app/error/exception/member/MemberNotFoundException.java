package app.error.exception.member;

import app.error.CustomException;
import app.error.ErrorCode;
import java.sql.SQLException;

public class MemberNotFoundException extends CustomException {

  public MemberNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
