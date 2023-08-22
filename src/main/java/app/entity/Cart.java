package app.entity;

import app.dto.comp.ProductAndMemberCompositeKey;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {



  @NonNull
  private Long memberId;
  @NonNull
  private Long productId;
  @NonNull
  private Integer productQuantity;


  public static Cart CartCompKeyBuilder(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Integer productQuantity) {
    return new Cart(productAndMemberCompositeKey.getMemberId(),
        productAndMemberCompositeKey.getProductId(), productQuantity);

  }
}
