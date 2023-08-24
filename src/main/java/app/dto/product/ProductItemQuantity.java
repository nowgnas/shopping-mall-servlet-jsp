package app.dto.product;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ProductItemQuantity {

  private Long id;
  private String name;
  private Integer quantity;
  private Long price;
  private String url;
}
