package app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Encryption {

  @NonNull private Long memberId;
  @NonNull private String email;
  @NonNull private String salt;

  public static Encryption from(Member member, String salt) {
    return Encryption.builder()
            .memberId(member.getId())
            .email(member.getEmail())
            .salt(salt)
            .build();
  }
}
