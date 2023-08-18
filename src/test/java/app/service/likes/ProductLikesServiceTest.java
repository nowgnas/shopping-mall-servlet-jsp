package app.service.likes;

import static org.junit.jupiter.api.Assertions.*;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.dto.product.ProductListItemOfLike;
import config.TestConfig;
import java.util.List;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

class ProductLikesServiceTest {
  private Logger log = Logger.getLogger("LikesServiceTest");
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();
  private LikesService likesService;

  @BeforeEach
  void beforeEach() throws Exception {
//    log.info("before");
    likesService = new ProductLikesService();
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("schema.sql", session);
    testConfig.init("init-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
//    log.info("after");
    session = GetSessionFactory.getInstance().openSession();
    testConfig.init("clear-data.sql", session);
  }

  @DisplayName("회원 찜목록 조회 테스트")
  @Test
  void getMemberLikes() throws Exception {
    likesService.addLikes(new ProductAndMemberCompositeKey(1L, 3L));
    likesService.addLikes(new ProductAndMemberCompositeKey(1L, 4L));

    List<ProductListItemOfLike> list = likesService.getMemberLikes(1L);

    assertEquals(list.get(0).getName(), "갤럭시북 20");
    assertEquals(list.get(1).getName(), "아이맥 20");
    assertEquals(list.get(2).getName(), "갤럭시 데스크탑 20");
  }

  @DisplayName("회원 찜 물품 조회 테스트")
  @Test
  void getMemberProductLikes() {
    boolean res = likesService.getMemberProductLikes(new ProductAndMemberCompositeKey(1L, 2L));

    assertTrue(res);
  }

  @Test
  void addLikes() {
  }

  @Test
  void removeLikes() {
  }

  @Test
  void removeSomeLikes() {
  }
}