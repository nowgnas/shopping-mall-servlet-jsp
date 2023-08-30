package app.exception.likes;

import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.apache.ibatis.exceptions.PersistenceException;

@Getter
public class LikesEntityDuplicateException extends PersistenceException {
  private static final String message = "이미 추가된 상품입니다.";

  public int getStatusCode() {
    return HttpServletResponse.SC_BAD_REQUEST;
  }

  public LikesEntityDuplicateException() {
    super(LikesEntityDuplicateException.message);
  }
}
