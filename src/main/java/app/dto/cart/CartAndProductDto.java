package app.dto.cart;


import app.entity.Cart;
import app.entity.Product;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;


@Getter
@Builder
@ToString
@AllArgsConstructor
public class CartAndProductDto {
    private Integer productQuantity;
    private Long productId;
    private Long categoryId;
    private String name;
    private String description;
    private Long price;
    private Long quantity;
    private String code;
    private Timestamp cartCreatedAt;
    private Timestamp productCreatedAt;

}
