package app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductListItemDto {
  private Long productId;
  private String name;
  private Long price;
  private String url;
  private boolean isThumbnail;
}
