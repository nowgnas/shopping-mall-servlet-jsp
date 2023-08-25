package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductAndMemberCompositeKey {

  private Long productId;
  private Long memberId;
}
