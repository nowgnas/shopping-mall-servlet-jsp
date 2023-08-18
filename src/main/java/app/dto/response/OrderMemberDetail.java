package app.dto.response;

import lombok.*;

import java.util.List;

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
    private AddressDetail addressDetail;
    private List<CouponDetail> coupons;
}
