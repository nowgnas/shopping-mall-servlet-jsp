package app.dto.cart;

import app.dto.comp.ProductAndMemberCompositeKey;
import java.util.List;

public class CartProductDto {

  private String productName;
  private Long productPrice;
  private Integer quantity;
  private Long totalProductPrice;
  private String imgUrl;


  public CartProductDto getCartProductDto() {
    totalProductPrice = quantity * productPrice;
  }


}
