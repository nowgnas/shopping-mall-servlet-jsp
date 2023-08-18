package app.dto.response;

import app.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberDetail {

  private Long id;
  private String email;
  private String name;
  private Long money;

  public static MemberDetail of(Member member) {
    return MemberDetail.builder()
        .id(member.getId())
        .email(member.getEmail())
        .name(member.getName())
        .money(member.getMoney())
        .build();
  }
}
