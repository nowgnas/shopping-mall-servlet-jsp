package app.service.product;

import app.dto.product.response.ProductDetailForOrder;
import app.dto.product.response.ProductDetailWithCategory;
import app.dto.product.response.ProductListWithPagination;
import app.dto.product.response.ProductSearchByKeyword;
import java.util.List;

public interface ProductService {

  ProductListWithPagination getProductList(Long userId, int currentPage, String sortOption)
      throws Exception;

  ProductDetailWithCategory getProductDetail(Long memberId, Long productId) throws Exception;

  /**
   * 상품 상세 정보 - 바로 구매 시
   *
   * @param productId
   * @param quantity
   * @return
   * @throws Exception
   */
  ProductDetailForOrder getProductDetailForOrder(Long productId, int quantity) throws Exception;

  List<ProductSearchByKeyword> getProductsByKeyword(String keyword) throws Exception;
}
