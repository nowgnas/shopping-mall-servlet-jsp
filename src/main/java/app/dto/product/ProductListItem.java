package app.dto.product;

import app.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductListItem extends BaseEntity {

  private String name;
  private Long price;
  private String url;
}
