package app.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductListItem {

  private String name;
  private Long price;
  private Long quantity;
}
