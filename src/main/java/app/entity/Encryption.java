package app.entity;

import lombok.*;

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
