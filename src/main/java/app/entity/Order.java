package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

  private Long id;
  @NonNull private Long memberId;
  @NonNull private String status;
}
