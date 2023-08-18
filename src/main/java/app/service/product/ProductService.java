package app.service.product;

import app.dto.product.ProductDetail;
import app.dto.product.ProductListItem;
import java.util.List;

public interface ProductService {
  List<ProductListItem> getProductsByLowerPrice() throws Exception;

  List<ProductListItem> getProductsByHigherPrice() throws Exception;

  List<ProductListItem> getProductsByDate() throws Exception;

  ProductDetail getProductDetail(Long productId) throws Exception;
}
