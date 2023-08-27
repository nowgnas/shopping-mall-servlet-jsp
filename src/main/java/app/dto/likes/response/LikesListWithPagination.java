package app.dto.likes.response;

import app.dto.paging.Pagination;
import app.dto.product.ProductListItemOfLike;
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
public class LikesListWithPagination {

  private List<ProductListItemOfLike> list;
  private Pagination paging;
}
