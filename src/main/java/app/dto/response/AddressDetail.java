package app.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressDetail {
  private boolean isDefault;
  private String roadName;
  private String addrDetail;
  private String zipCode;
}
