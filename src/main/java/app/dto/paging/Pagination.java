package app.dto.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pagination {
  private int currentPage; // 현재 페이지
  @Builder.Default private int perPage = 10; // 페이지당 보여줄 개수
  private int totalPage; // 전체 페이지
}
