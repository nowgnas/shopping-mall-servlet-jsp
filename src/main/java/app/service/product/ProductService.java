package app.service.product;

import app.dto.paging.Pagination;
import app.dto.product.ProductDetail;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductListWithPagination;
import app.enums.SortOption;
import java.util.List;

public interface ProductService {

  ProductListWithPagination<List<ProductListItem>, Pagination> getProductList(
      Long userId, int currentPage, SortOption sortOption) throws Exception;

  ProductDetail getProductDetail(Long productId) throws Exception;
}
