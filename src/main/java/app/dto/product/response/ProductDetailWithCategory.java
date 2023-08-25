package app.dto.product.response;

import app.dto.category.ProductCategory;
import app.dto.product.ProductDetail;
import app.entity.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class ProductDetailWithCategory {

  private ProductCategory category;
  private ProductDetail detail;

  public static ProductDetailWithCategory getProductDetail(
      List<Category> categories, ProductDetail detail) {
    Map<Integer, String> category = new HashMap<>();
    int idx = 1;
    for (Category item : categories) {
      category.put(idx, item.getName());
      idx++;
    }
    ProductCategory productCategory = ProductCategory.builder().categoryList(category).build();

    return ProductDetailWithCategory.builder().category(productCategory).detail(detail).build();
  }
}
