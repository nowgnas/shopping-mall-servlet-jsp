package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Encryption {

  private Long memberId;
  @NonNull private String salt;

  public static Encryption from(Long memberId, String salt) {
    return Encryption.builder().memberId(memberId).salt(salt).build();
  }
}
