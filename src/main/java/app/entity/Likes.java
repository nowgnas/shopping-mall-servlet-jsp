package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

  @NonNull private Long memberId;
  @NonNull private Long productId;
}
