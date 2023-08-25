package app.exception.likes;

import org.apache.ibatis.exceptions.PersistenceException;

public class LikesEntityDuplicateException extends PersistenceException {
  private static final String message = "이미 추가된 상품입니다.";

  public LikesEntityDuplicateException() {
    super(LikesEntityDuplicateException.message);
  }
}
