package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrder extends BaseEntity {

  private Long id;
  @NonNull private Long productId;
  @NonNull private Long orderId;
  @NonNull private Long price;
  @NonNull private Integer quantity;
}
