package app.entity;

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
public class Address extends BaseEntity {

  private Long id;
  @NonNull
  private Long memberId;
  @Builder.Default
  private boolean isDefault = false;
  @NonNull
  private String roadName;
  @NonNull
  private String addrDetail;
  @NonNull
  private String zipCode;
}
