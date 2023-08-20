package app.service.product;

import app.dto.product.ProductDetail;
import app.dto.product.response.ProductListWithPagination;
import app.enums.SortOption;

public interface ProductService {

  ProductListWithPagination getProductList(Long userId, int currentPage, SortOption sortOption)
      throws Exception;

  ProductDetail getProductDetail(Long productId) throws Exception;
}
