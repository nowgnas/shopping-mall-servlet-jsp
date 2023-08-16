package app.entity;

import app.enums.OrderStatus;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

  private Long id;
  @NonNull private Long memberId;
  @Builder.Default private String status = OrderStatus.PENDING.name();
}
