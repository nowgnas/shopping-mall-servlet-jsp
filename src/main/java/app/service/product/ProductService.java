package app.service.product;

import app.dto.product.response.ProductDetail;
import app.dto.product.response.ProductListItem;
import java.util.List;

public interface ProductService {
  List<ProductListItem> getProductsByLowerPrice() throws Exception;

  List<ProductListItem> getProductsByHigherPrice() throws Exception;

  List<ProductListItem> getProductsByDate() throws Exception;

  ProductDetail getProductDetail(Long productId) throws Exception;
}
