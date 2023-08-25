package app.entity;

import app.dto.product.response.ProductSearchByKeyword;
import app.utils.ModelMapperStrict;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  private Long id;
  @NonNull private Long categoryId;
  @NonNull private String name;
  @Builder.Default private String description = "";
  @NonNull private Long price;
  @NonNull private Long quantity;
  @NonNull private String code;

  public static List<ProductSearchByKeyword> productSearchByKeyword(List<Product> products) {
    ModelMapper modelMapper = ModelMapperStrict.strictMapper();
    List<ProductSearchByKeyword> list = new ArrayList<>();
    products.forEach(
        v -> {
          list.add(modelMapper.map(v, ProductSearchByKeyword.class));
        });
    return list;
  }

  public void updateQuantity(Long quantity) {
    this.quantity = quantity;
  }
}
