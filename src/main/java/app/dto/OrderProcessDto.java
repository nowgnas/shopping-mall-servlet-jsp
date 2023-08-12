package app.dto;

import app.entity.Cart;
import app.entity.Coupon;
import app.entity.Member;
import app.entity.Order;
import app.entity.Product;

public class OrderProcessDto {
  private Member member;
  private Order order;
  private Product product;
  private Cart cart;
  private Coupon counpon;
}
