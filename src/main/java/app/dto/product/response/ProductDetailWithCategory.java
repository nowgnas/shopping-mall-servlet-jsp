package app.dto.product.response;

import app.dto.category.ProductCategory;
import app.dto.product.ProductDetail;
import app.entity.Category;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailWithCategory {
  private ProductCategory category;
  private ProductDetail detail;

  public static ProductDetailWithCategory getProductDetail(
      List<Category> categories, ProductDetail detail) {
    ProductCategory productCategory =
        ProductCategory.builder()
            .depth1Name(categories.get(0).getName())
            .depth2Name(categories.get(1).getName())
            .depth3Name(categories.get(2).getName())
            .build();

    return ProductDetailWithCategory.builder().category(productCategory).detail(detail).build();
  }
}
