package app.service.category;

import app.dto.product.response.ProductListWithPagination;
import app.entity.Category;
import java.util.List;

public interface CategoryService {
  List<Category> getAllCategory() throws Exception;

  ProductListWithPagination getProductListByCategoryName(
      Long memberId, String keyword, int curPage);
}
