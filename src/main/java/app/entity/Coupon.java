package app.entity;

import app.enums.CouponStatus;
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
public class Coupon extends BaseEntity {

  private Long id;
  @NonNull
  private Long memberId;
  @NonNull
  private String name;
  @NonNull
  private String discountPolicy;
  @NonNull
  private Integer discountValue;
  @Builder.Default
  private String status = CouponStatus.UNUSED.name();

  public void updateStatus(String status) {
    this.status = status;
  }
}
