package app.service.product;

import app.dto.paging.Pagination;
import app.dto.product.ProductDetail;
import app.dto.product.ProductListItem;
import app.dto.product.response.ProductListWithPagination;
import java.util.List;

public interface ProductService {
  List<ProductListItem> getProductsByLowerPrice() throws Exception;

  ProductListWithPagination<List<ProductListItem>, Pagination> getProductsByHigherPrice()
      throws Exception;

  List<ProductListItem> getProductsByDate() throws Exception;

  ProductDetail getProductDetail(Long productId) throws Exception;
}
