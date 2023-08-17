package app.dto.comp;

import lombok.Getter;

@Getter
public class ProductAndMemberCompositeKey {
  private Long memberId;
  private Long productId;
}
