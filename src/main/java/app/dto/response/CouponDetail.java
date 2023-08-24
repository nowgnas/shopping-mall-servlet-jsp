package app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponDetail {

  private Long id;
  private String name;
  private String discountPolicy;
  private Integer discountValue;
  private String status;
}
