package app.exception.product;

import app.exception.DomainException;

public class CategoryListNotFound extends DomainException {
  private static final String message = "해당 키워드의 카테고리가 존재하지 않습니다";

  public CategoryListNotFound() {
    super(message);
  }

  @Override
  public int getStatusCode() {
    return 0;
  }
}
