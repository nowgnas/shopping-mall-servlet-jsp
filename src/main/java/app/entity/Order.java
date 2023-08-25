package app.entity;

import app.enums.OrderStatus;
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
public class Order extends BaseEntity {

  private Long id;
  @NonNull
  private Long memberId;
  private Long couponId;
  @Builder.Default
  private String status = OrderStatus.PENDING.name();

  public void updateStatus(String status) {
    this.status = status;
  }
}
