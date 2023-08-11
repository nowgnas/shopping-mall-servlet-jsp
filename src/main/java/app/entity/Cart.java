package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

  @NonNull private Long memberId;
  @NonNull private Long productId;
  @NonNull private Integer productQuantity;
}
