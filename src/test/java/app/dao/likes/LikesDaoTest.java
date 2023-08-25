package app.dao.likes;

import static org.junit.jupiter.api.Assertions.*;

import app.entity.ProductAndMemberCompositeKey;
import app.entity.Likes;
import config.TestConfig;
import java.util.List;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

class LikesDaoTest {

  private Logger log = Logger.getLogger("LikesDaoTest");
  private SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();
  private LikesDaoFrame<ProductAndMemberCompositeKey, Likes> likesDao;

  @BeforeEach
  void beforeEach() throws Exception {
    likesDao = LikesDao.getInstance();
    session = sessionFactory.openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("likes/init-likes-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = sessionFactory.openSession();
    testConfig.init("clear-data.sql", session);
  }

  @DisplayName("insert test")
  @Test
  void insert() throws Exception {
    int res = likesDao.insert(
        Likes.builder()
            .memberId(1L)
            .productId(1L)
            .build()
        , session
    );
    int res2 = likesDao.insert(
        Likes.builder()
            .memberId(1L)
            .productId(1L)
            .build()
        , session
    );
    session.commit();

    assertTrue(res == 1);
//    log.info("[insert test] insert result : " + (res == 1 ? "true" : "false"));
  }

  @DisplayName("delete test")
  @Test
  void deleteById() throws Exception {
    int res = likesDao.deleteById(
        ProductAndMemberCompositeKey.builder()
            .memberId(1L)
            .productId(2L)
            .build()
        , session
    );
    session.commit();

    assertTrue(res == 1);
//    log.info("[delete test] delete result : " + (res == 1 ? "true" : "false"));
  }

  @DisplayName("select test")
  @Test
  void selectById() throws Exception {
    Likes inputLikes = Likes.builder()
        .memberId(1L)
        .productId(3L)
        .build();

    likesDao.insert(inputLikes, session);
    session.commit();

    Likes ouputLikes = likesDao.selectById(
            ProductAndMemberCompositeKey.builder()
                .memberId(1L)
                .productId(3L)
                .build()
            , session)
        .get();

    assertEquals(inputLikes.getMemberId(), ouputLikes.getMemberId());
    assertEquals(inputLikes.getProductId(), ouputLikes.getProductId());
//    log.info("[select test] member id : " + likes.getMemberId());
//    log.info("[select test] product id : " + likes.getProductId());
  }

  @DisplayName("select all test")
  @Test
  void selectAll() throws Exception {

    // basic init Likes(1L, 2L)

    likesDao.insert(
        Likes.builder()
            .memberId(1L)
            .productId(3L)
            .build()
        , session);

    likesDao.insert(
        Likes.builder()
            .memberId(1L)
            .productId(4L)
            .build()
        , session);

    session.commit();

    List<Long> list = likesDao.selectAllProduct(1L, session);
    long idx = 2L;

    // total 3 rows
    for (Long productId : list) {
      assertEquals(productId, idx++);
//      log.info("[select all test] product id : " + productId);
    }
  }
}