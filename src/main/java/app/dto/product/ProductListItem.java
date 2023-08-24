package app.dto.product;

import app.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
