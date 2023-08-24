package web.controller.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MemberValidation {

  private static final String EMAIL_REGEX =
      "^[a-zA-Z0-9_+&*-]+(?:\\."
          + "[a-zA-Z0-9_+&*-]+)*@"
          + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
          + "A-Z]{2,5}$";

  private static final String PASSWORD_REGEX =
      "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$";

  private static final String NAME_REGEX =
      "^[ㄱ-ㅎ가-힣]{2,20}$";

  public static boolean isValidEmail(String email) {
    Pattern pattern = Pattern.compile(EMAIL_REGEX);
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }

  public static boolean isValidPassword(String password) {
    Pattern pattern = Pattern.compile(PASSWORD_REGEX);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }

  public static boolean isValidName(String name) {
    Pattern pattern = Pattern.compile(NAME_REGEX);
    Matcher matcher = pattern.matcher(name);
    return matcher.matches();
  }
}
