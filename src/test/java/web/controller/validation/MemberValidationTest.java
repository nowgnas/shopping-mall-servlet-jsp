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
}
