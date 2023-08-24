package app.service.likes;

import static org.junit.jupiter.api.Assertions.*;

import app.entity.ProductAndMemberCompositeKey;
import config.TestConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.GetSessionFactory;

class ProductLikesServiceTest {

  private Logger log = Logger.getLogger("LikesServiceTest");
  private SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private SqlSession session;
  private final TestConfig testConfig = new TestConfig();
  private LikesService likesService;

  @BeforeEach
  void beforeEach() throws Exception {
    likesService = new ProductLikesService();
    session = sessionFactory.openSession();
    testConfig.init("schema.sql", session);

    // 기본 찜 정보 : init Likes(1L, 2L)
    testConfig.init("init-data.sql", session);
  }

  @AfterEach
  void afterEach() throws Exception {
    session = sessionFactory.openSession();
    testConfig.init("clear-data.sql", session);
  }

  @DisplayName("회원 찜목록 조회 테스트")
  @Test
  void getMemberLikes() throws Exception {
    /*
    productId : name
            2 : "갤럭시북 20"
            3 : "아이맥 20"
            4 : "갤럭시 데스크탑 20"
     */
    
    // 찜 정보 추가
    likesService.addLikes(
        ProductAndMemberCompositeKey.builder()
            .memberId(1L)
            .productId(3L)
            .build()
    );

    likesService.addLikes(
        ProductAndMemberCompositeKey.builder()
            .memberId(1L)
            .productId(4L)
            .build()
    );

    // 찜 목록 가져오기
    // 썸네일 없을 경우 상품 목록을 가져오지 못 하는 점 수정 필요
//    List<ProductListItemOfLike> list = likesService.getMemberLikes(1L);

    /*
    assertEquals(list.get(0).getName(), "갤럭시북 20");
    assertEquals(list.get(1).getName(), "아이맥 20");
    assertEquals(list.get(2).getName(), "갤럭시 데스크탑 20");
    */
  }

  @DisplayName("회원의 물품 찜 여부 조회 테스트")
  @Test
  void getMemberProductLikes() throws Exception {

    // 회원 id 1, 상품 id 2인 찜 정보 여부 조회
    boolean res = likesService.getMemberProductLikes(
        ProductAndMemberCompositeKey.builder()
            .memberId(1L)
            .productId(2L)
            .build()
    );

    assertTrue(res);
  }

  @DisplayName("회원 찜 추가 테스트")
  @Test
  void addLikes() throws Exception {
    
    // 회원 id 1, 상품 id 3인 찜 정보 추가
    int res = likesService.addLikes(
        ProductAndMemberCompositeKey.builder()
            .memberId(1L)
            .productId(3L)
            .build()
    );

    // 추가 로직 작동여부 파악
    assertEquals(res, 1);

    // 추가된 찜 정보 조회
    assertTrue(likesService.getMemberProductLikes(
        ProductAndMemberCompositeKey.builder()
            .memberId(1L)
            .productId(3L)
            .build()
        )
    );
  }

  @DisplayName("회원 찜 삭제 테스트")
  @Test
  void removeLikes() throws Exception {
    
    // 회원 id 1, 상품 id 2인 찜 정보 삭제
    int res = likesService.removeLikes(
        ProductAndMemberCompositeKey.builder()
            .memberId(1L)
            .productId(2L)
            .build()
    );

    // 삭제 로직 작동여부 파악
    assertEquals(res, 1);
    
    // 삭제된 찜 정보 조회, 없어야 하므로 !연산자 추가
    assertTrue(!likesService.getMemberProductLikes(
            ProductAndMemberCompositeKey.builder()
                .memberId(1L)
                .productId(2L)
                .build()
        )
    );
  }

  @DisplayName("회원 찜 벌크 삭제 테스트")
  @Test
  void removeSomeLikes() throws Exception {

    // 회원 id 1, 상품 id 2 ~ 4 세팅
    List<ProductAndMemberCompositeKey> keyList = new ArrayList<>();
    for(long i = 2; i <= 4; i++) {
      keyList.add(
          ProductAndMemberCompositeKey.builder()
              .memberId(1L)
              .productId(i)
              .build()
      );
    }

    // 회원 id 1, 상품 id 3
    likesService.addLikes(keyList.get(1));

    // 회원 id 1, 상품 id 4
    likesService.addLikes(keyList.get(2));

    int res = likesService.removeSomeLikes(keyList);

    // 삭제 로직 작동여부 파악
    assertEquals(res, 3);
  }
}