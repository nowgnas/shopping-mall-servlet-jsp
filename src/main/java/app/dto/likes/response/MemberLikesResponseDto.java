package app.dto.likes.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLikesResponseDto {
  private Long productId;
  private String productName;
  private Long productPrice;
  private String imgUrl;
}
