package app.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

  private Long id;
  private Long parentId;
  @NonNull private String name;
  @NonNull private Integer level;
}
