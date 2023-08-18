package app.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderMemberDetail {
    private MemberDetail memberDetail;
    private AddressDetail addressDetail;
    private CouponDetail couponDetail;
}
