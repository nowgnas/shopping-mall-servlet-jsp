package web.controller.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberValidationTest {

  @Test
  @DisplayName("이메일 형식이 올바른 경우 true를 반환 한다.")
  void email_validation_success() throws Exception {
    String inputEmail = "abc@naver.com";
    boolean validEmail = MemberValidation.isValidEmail(inputEmail);
    Assertions.assertTrue(validEmail);
  }

  @Test
  @DisplayName("이메일 형식이 정규식에 맞지 않는 경우 false를 반환 한다.")
  void email_validation_fail() throws Exception {
    String inputEmail = "abc@com";
    boolean validEmail = MemberValidation.isValidEmail(inputEmail);
    Assertions.assertFalse(validEmail);
  }

  @Test
  @DisplayName("비밀번호 정규식 체크에 성공한 경우 true를 반환 한다.")
  void password_validation_success() throws Exception {
    String password = "a1234567";
    boolean validPassword = MemberValidation.isValidPassword(password);
    Assertions.assertTrue(validPassword);
  }

  @Test
  @DisplayName("비밀번호 정규식 체크에 실패 한 경우 false를 반환 한다.")
  void password_validation_fail() throws Exception {
    String password = "123456789";
    String password2 = "a123";
    boolean validPassword = MemberValidation.isValidPassword(password);
    boolean validPassword2 = MemberValidation.isValidPassword(password2);
    Assertions.assertFalse(validPassword);
    Assertions.assertFalse(validPassword2);
  }

  @Test
  @DisplayName("이름 정규식 체크에 성공한 경우 true를 반환 한다.")
  void name_validation_success() throws Exception {
    String name = "김한";
    String name2 = "가나다라마바사아자차카타파하가나다라마바";
    boolean validName = MemberValidation.isValidName(name);
    boolean validName2 = MemberValidation.isValidName(name2);
    Assertions.assertTrue(validName);
    Assertions.assertTrue(validName2);
  }
}
