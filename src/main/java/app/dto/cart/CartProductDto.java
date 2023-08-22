package app.dto.cart;


import app.dto.product.ProductItemQuantity;
import app.entity.Cart;
import app.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class CartAndProductListDto {

  private List<Cart> cartList;
  private List<Product> productList;

  private CartAndProductListDto(){

  }

  public static  CartAndProductListDto getAllCartInfoAndProductInfo(List<Cart> cartList , List<Product> productList) {
    return  CartAndProductListDto.builder().cartList(cartList).productList(productList).build();

  }



}
