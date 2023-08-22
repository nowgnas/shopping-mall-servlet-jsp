package app.dao.likes;

import static org.junit.jupiter.api.Assertions.*;

import app.dto.comp.ProductAndMemberCompositeKey;
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
//    log.info("before");
    likesDao = LikesDao.getInstance();
    session = sessionFactory.openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
//    log.info("after");
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @DisplayName("insert test")
  @Test
  void insert() throws Exception {
    int res = likesDao.insert(new Likes(1L, 1L), session);
    session.commit();

    assertTrue(res == 1);
//    log.info("[insert test] insert result : " + (res == 1 ? "true" : "false"));
  }

  @DisplayName("delete test")
  @Test
  void deleteById() throws Exception {
    int res = likesDao.deleteById(new ProductAndMemberCompositeKey(2L, 1L), session);
    session.commit();

    assertTrue(res == 1);
//    log.info("[delete test] delete result : " + (res == 1 ? "true" : "false"));
  }

  @DisplayName("select test")
  @Test
  void selectById() throws Exception {
    Likes inputLikes = new Likes(1L, 3L);
    likesDao.insert(inputLikes, session);
    session.commit();

    Likes ouputLikes = likesDao.selectById(
            new ProductAndMemberCompositeKey(3L, 1L)
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
    likesDao.insert(new Likes(1L, 3L), session);
    likesDao.insert(new Likes(1L, 4L), session);
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