package app.dto.product.response;

import app.dto.paging.Pagination;
import app.dto.product.ProductListItem;
import java.util.List;
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
public class ProductListWithPagination<K extends List<ProductListItem>, V extends Pagination> {
  K item;
  V paging;

  public static ProductListWithPagination<List<ProductListItem>, Pagination> makeListWithPaging(
      List<ProductListItem> item, Pagination pagination, int totalPages) {
    Pagination paging =
        Pagination.builder()
            .perPage(pagination.getPerPage())
            .totalPage(totalPages)
            .currentPage(pagination.getCurrentPage())
            .build();
    return ProductListWithPagination.<List<ProductListItem>, Pagination>builder()
        .item(item)
        .paging(paging)
        .build();
  }
}
