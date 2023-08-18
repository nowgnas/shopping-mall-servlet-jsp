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
  private final TestConfig testConfig = new TestConfig();
  private LikesDaoFrame<ProductAndMemberCompositeKey, Likes> likesDao;

  @BeforeEach
  void beforeEach() throws Exception {
    log.info("before");
    likesDao = new LikesDao();
    SqlSession session = sessionFactory.openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }

//  @AfterEach
//  void afterEach() throws Exception {
//    log.info("after");
//    session = GetSessionFactory.getInstance().openSession();
//    testConfig.init("clear-data.sql", session);
//  }

//  @Test
//  void insert() throws Exception {
//    SqlSession session2 = GetSessionFactory.getInstance().openSession();
//
//    log.info("insert");
//    int res = likesDao.insert(new Likes(1L, 1L), session2);
//    session2.commit();
//    session2.close();
//    log.info("insert result : " + res);
//
//    SqlSession session3 = GetSessionFactory.getInstance().openSession();
//    Likes likes = likesDao.selectById(new ProductAndMemberCompositeKey(1L, 1L), session3).get();
//    log.info(likes.getMemberId()+"");
//  }

//  @Test
//  void update() {
//  }
//
//  @Test
//  void deleteById() {
//  }
//
  @Test
  void selectById() throws Exception {
    SqlSession session = sessionFactory.openSession();
//    likesDao.insert(new Likes(1L, 3L), session);
//    session.commit();
//
//    List<Likes> list = likesDao.selectAll(1L, session);
    ProductAndMemberCompositeKey productAndMemberCompositeKey = new ProductAndMemberCompositeKey(1L,
        2L);
    System.out.println(productAndMemberCompositeKey.getMemberId());
    System.out.println(productAndMemberCompositeKey.getProductId());
    Likes getLikes = likesDao.selectById(
        productAndMemberCompositeKey
        , session
    ).orElse(new Likes(5L, 5L));

    log.info(getLikes.getMemberId() + "");

//    int res = likesDao.deleteById(new ProductAndMemberCompositeKey(1L, 2L), session);
//    log.info(res+"");
  }
//
//  @Test
//  void selectAll() {
//  }
//
//  @Test
//  void testSelectAll() {
//  }
}