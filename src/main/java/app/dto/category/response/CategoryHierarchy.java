package app.dto.category.response;

import app.dto.category.MiddleCategory;
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
public class CategoryHierarchy {
  private Long id;
  private String name;
  private MiddleCategory middleCategory;
}
