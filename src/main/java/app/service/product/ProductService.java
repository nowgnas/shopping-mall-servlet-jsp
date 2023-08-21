package app.service.product;

import app.dto.product.response.ProductDetailWithCategory;
import app.dto.product.response.ProductListWithPagination;
import app.enums.SortOption;

public interface ProductService {

  ProductListWithPagination getProductList(Long userId, int currentPage, SortOption sortOption)
      throws Exception;

  ProductDetailWithCategory getProductDetail(Long memberId, Long productId) throws Exception;
}
