package app.dto.member.request;

import app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberRegisterDto {

  private String email;
  private String password;
  private String name;

  public Member toEntity(String hashedPassword) {
    return Member.builder().email(email).password(hashedPassword).name(name).build();
  }
}
