package app.service.cart;

import app.dao.CartDaoFrame;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Cart;
import app.error.ErrorCode;
import app.utils.GetSessionFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartServiceImpl implements CartService {

  private CartDaoFrame<ProductAndMemberCompositeKey, Cart> cartDaoFrame;

  //change the return type
  @Override
  public List<Cart> getCartProductListByMember(
      List<ProductAndMemberCompositeKey> productAndMemberCompositeKeys) throws Exception {
    List<Cart> cartsByMember = cartDaoFrame.selectAll(GetSessionFactory.getInstance().openSession());
    List<Long> productIdList = cartsByMember.stream().map(Cart::getProductId).collect(Collectors.toList());


    //get all CartList => get all product Id
    // request List< product Id>
    // get List<ProductInfo> List
    //make ProductsInfoInCart 반환
  }

  @Override
  public ErrorCode putItemIntoCart(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      Integer quantity) throws Exception {
    int count = cartDaoFrame.insert(Cart.CartCompKeyBuilder(productAndMemberCompositeKey, quantity),
        GetSessionFactory.getInstance().openSession());
        return count > 0 ? ErrorCode.valueOfCode("4002") : null;
  }

  @Override
  public ErrorCode updateQuantityOfCartProduct(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, Integer updateQuantity)
      throws Exception {
    Cart cart = cartDaoFrame.selectById(productAndMemberCompositeKey,GetSessionFactory.getInstance()
        .openSession()).orElseThrow(ErrorCode.valueOfCode("4001"));




    //get Product stock from Cart(SelectOne)
    //compare the Product Stock with updateQuantity
    //If violate under the two condition return ErrorCode
    //or return null;

    //if product.stock - updateQuantity under 0 => return ErrorCode => OutOfStock
    //if updateQuantity is under 0  => if currentQuantity + updateQuantity under 0 => just remove the product in the cart
  }

  @Override
  public ErrorCode delete(ProductAndMemberCompositeKey productAndMemberCompositeKey)
      throws Exception {

    int count = cartDaoFrame.deleteById(productAndMemberCompositeKey,
        GetSessionFactory.getInstance()
            .openSession());

    return count > 0 ? ErrorCode.valueOfCode("4001") : null;

  }
}
