package app.dto.product;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductDetail {
  private Long id;
  private String name;
  private Long price;
  private Integer categoryId;
  private String description;
  private String url;
  private Long quantity;
  private Boolean isLiked;
  private String code;
}
