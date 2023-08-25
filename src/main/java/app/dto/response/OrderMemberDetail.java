package app.dto.response;

import java.util.List;
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
public class OrderMemberDetail {

  private Long id;
  private String email;
  private String name;
  private Long money;
  private AddressDetail address;
  private List<CouponDetail> coupons;
}
