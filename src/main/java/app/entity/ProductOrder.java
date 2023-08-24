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
public class ProductOrder extends BaseEntity {

  private Long id;
  @NonNull
  private Long productId;
  @NonNull
  private Long orderId;
  @NonNull
  private Long price;
  @NonNull
  private Long quantity;
}
