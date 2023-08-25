package app.dto.product;

import app.entity.BaseEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductListItem extends BaseEntity {
  private Long id;
  private String name;
  private Long price;
  private String url;
  private Boolean isLiked;
}
