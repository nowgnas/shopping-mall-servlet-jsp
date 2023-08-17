package app.service.likes;

import static org.junit.jupiter.api.Assertions.*;

import app.dao.Likes.LikesDao;
import app.dao.Likes.LikesDaoFrame;
import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Likes;
import app.error.CustomException;
import app.error.ErrorCode;
import app.utils.GetSessionFactory;
import java.sql.SQLException;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductLikesServiceTest {

  SqlSession session;
  LikesDaoFrame<ProductAndMemberCompositeKey, Likes> likesDao;

  @BeforeEach
  void beforeEach() {
    likesDao = new LikesDao();
  }

  @Test
  void getMemberLikes() {

  }

  @Test
  void getMemberProductLikes() {
  }

  @Test
  void addLikesTest() {
    try {
      session = GetSessionFactory.getInstance().openSession();
      Likes likes1 = new Likes(0L, 0L);
      Likes likes2 = new Likes(1L, 0L);

      likesDao.insert(likes1, session);
      likesDao.insert(likes2, session);

      Likes getLikes = likesDao.selectAll(0L, session).get(0);

      assertEquals(likes1, getLikes);

    } catch (SQLException e) {
      e.printStackTrace();
      throw new CustomException(ErrorCode.PRODUCT_IS_NOT_VALID);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void removeLikes() {
  }

  @Test
  void removeSomeLikes() {
  }
}