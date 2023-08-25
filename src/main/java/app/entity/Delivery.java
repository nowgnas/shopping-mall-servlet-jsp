package app.entity;

import app.enums.DeliveryStatus;
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
public class Delivery extends BaseEntity {

  @NonNull
  private Long orderId;
  @NonNull
  private String roadName;
  @NonNull
  private String addrDetail;
  @NonNull
  private String zipCode;
  @Builder.Default
  private String status = DeliveryStatus.PENDING.name();

  public void updateStatus(String status) {
    this.status = status;
  }
}
