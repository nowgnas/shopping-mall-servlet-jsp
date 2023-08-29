package app.service.product;

import app.dto.product.response.ProductDetailWithCategory;
import app.dto.product.response.ProductListWithPagination;

public interface ProductService {

  ProductListWithPagination getProductList(Long userId, int currentPage, String sortOption)
      throws Exception;

  ProductDetailWithCategory getProductDetail(Long memberId, Long productId) throws Exception;

  ProductListWithPagination getProductsByKeyword(String keyword, Long memberId, int curPage)
      throws Exception;
}
