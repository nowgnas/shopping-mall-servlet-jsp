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
}
