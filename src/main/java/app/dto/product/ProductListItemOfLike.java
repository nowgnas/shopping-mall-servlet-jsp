package app.dto.product;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductListItemOfLike {

  private Long id;
  private String name;
  private Long price;
  private String url;
}
