package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Product extends BaseEntity {

  private Long id;
  @NonNull private Long categoryId;
  @NonNull private String name;
  @Builder.Default private String description = "";
  @NonNull private Long price;
  @NonNull private Long quantity;
  @NonNull private String code;
}
