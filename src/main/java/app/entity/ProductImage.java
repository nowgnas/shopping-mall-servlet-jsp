package app.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductImage extends BaseEntity {

  private Long id;
  @NonNull
  private Long productId;
  @NonNull
  private String url;
  @Builder.Default
  private boolean isThumbnail = false;
}
