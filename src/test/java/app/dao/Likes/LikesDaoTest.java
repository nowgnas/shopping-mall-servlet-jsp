package app.dao.Likes;

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
    likesDao = new LikesDao();
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

  @Test
  void insert() throws Exception {
    int res = likesDao.insert(new Likes(1L, 1L), session);
    session.commit();

    log.info("[insert test] insert result : " + (res == 1 ? "true" : "false"));
  }

  @Test
  void deleteById() throws Exception {
    int res = likesDao.deleteById(new ProductAndMemberCompositeKey(1L, 2L), session);
    session.commit();

    log.info("[delete test] delete result : " + (res == 1 ? "true" : "false"));
  }

  @Test
  void selectById() throws Exception {
    Likes likes = likesDao.selectById(new ProductAndMemberCompositeKey(1L, 2L), session).get();
    log.info("[select test] member id : " + likes.getMemberId());
    log.info("[select test] product id : " + likes.getProductId());
  }

  @Test
  void selectAll() throws Exception {
    likesDao.insert(new Likes(1L, 3L), session);
    likesDao.insert(new Likes(1L, 4L), session);
    session.commit();

    List<Likes> list = likesDao.selectAll(1L, session);
    for (Likes likes : list) {
      log.info("[select all test] product id : " + likes.getProductId());
    }
  }
}