package app.dao.Likes;

import static org.junit.jupiter.api.Assertions.*;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Likes;
import config.TestConfig;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

class LikesDaoTest {
  private Logger log = Logger.getLogger("LikesDaoTest");
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();
  private LikesDaoFrame<ProductAndMemberCompositeKey, Likes> likesDao;

  @BeforeEach
  void beforeEach() throws Exception {
    log.info("before");
    likesDao = new LikesDao();
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    log.info("after");
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @Test
  void insert() throws Exception {
    log.info("insert");
    int res = likesDao.insert(new Likes(1L, 1L), session);
    session.commit();
    log.info(res + "");
//    Likes likes = likesDao.selectAll(1L, session).get(0);
//    log.info(likes.getMemberId()+"");
  }

//  @Test
//  void update() {
//  }
//
//  @Test
//  void deleteById() {
//  }
//
//  @Test
//  void selectById() {
//  }
//
//  @Test
//  void selectAll() {
//  }
//
//  @Test
//  void testSelectAll() {
//  }
}