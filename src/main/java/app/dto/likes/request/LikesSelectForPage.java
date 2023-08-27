package app.dto.likes.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikesSelectForPage {
  private Long memberId;
  private Integer start;
  private Integer PerPage;
}
