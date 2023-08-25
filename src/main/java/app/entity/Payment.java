package app.entity;

import app.enums.PaymentType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

  private Long id;
  @NonNull private Long orderId;
  @NonNull private Long actualAmount;
  @Builder.Default private String type = PaymentType.CASH.name();
}
