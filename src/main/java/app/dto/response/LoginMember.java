package app.dto.response;

import app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginMember {

  private Long id;
  private String email;
  private String name;
  private Long money;

  public static LoginMember of(Member member) {
    return LoginMember.builder()
        .id(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .money(member.getMoney())
        .build();
  }
}
