package app.entity;

import app.enums.PaymentType;
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
public class Payment extends BaseEntity {

  private Long id;
  @NonNull
  private Long orderId;
  @NonNull
  private Long actualAmount;
  @Builder.Default
  private String type = PaymentType.CASH.name();
}
