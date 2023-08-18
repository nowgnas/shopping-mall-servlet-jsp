package app.dto.comp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAndMemberCompositeKey {
  private Long memberId;
  private Long productId;
}
