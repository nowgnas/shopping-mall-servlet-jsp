package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage extends BaseEntity {

  private Long id;
  @NonNull private Long productId;
  @NonNull private String url;
  @Builder.Default private boolean isThumbnail = false;
}
