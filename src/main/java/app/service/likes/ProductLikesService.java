package app.service.likes;

import app.dao.likes.LikesDao;
import app.dao.likes.LikesDaoFrame;
import app.dao.product.ProductDao;
import app.dao.product.ProductDaoFrame;
import app.dto.likes.request.LikesSelectForPage;
import app.dto.likes.response.LikesListWithPagination;
import app.dto.paging.Pagination;
import app.dto.product.ProductListItemOfLike;
import app.entity.Likes;
import app.entity.Product;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.likes.LikesEntityNotFoundException;
import app.utils.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.session.SqlSessionFactory;

public class ProductLikesService implements LikesService {

  private final LikesDaoFrame<ProductAndMemberCompositeKey, Likes> likesDao;
  private final ProductDaoFrame<Long, Product> productDao;
  private final SqlSessionFactory sessionFactory = GetSessionFactory.getInstance();
  private SqlSession session;

  public ProductLikesService() {
    likesDao = LikesDao.getInstance();
    productDao = ProductDao.getInstance();
  }

  /**
   * 회원의 찜한 상품 목록 반환
   *
   * @param memberId 회원 id
   * @return 리스트 : 상품 id, 이름, 가격, 이미지경로
   */
  @Override
  public LikesListWithPagination getMemberLikes(Long memberId, Integer curPage) throws Exception {
    try {
      session = sessionFactory.openSession();

      int perPage = 5;
      int totalPage = likesDao.selectTotalPage(memberId, perPage, session);
      System.out.println("totalPage : " + totalPage);

      int start = (curPage - 1) * perPage;
      LikesSelectForPage likesSelectForPage =
          LikesSelectForPage.builder().memberId(memberId).start(start).PerPage(perPage).build();

      List<Long> productIdList = likesDao.selectAllProduct(likesSelectForPage, session);
      List<ProductListItemOfLike> memberLikesList =
          productDao.selectProductListItemOfLike(productIdList, session);

      Pagination paging =
          Pagination.builder()
              .currentPage(Math.toIntExact(curPage))
              .totalPage(totalPage)
              .perPage(perPage)
              .build();

      return LikesListWithPagination.builder().list(memberLikesList).paging(paging).build();
    } catch (LikesEntityNotFoundException e) {
      e.printStackTrace();
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      session.close();
    }
  }

  /**
   * 회원의 상품 찜 여부 반환
   *
   * @param productAndMemberCompositeKey 상품 id, 회원 id로 이루어진 복합키
   * @return true(찜 O), false(찜 X)
   * @throws Exception
   */
  @Override
  public boolean getMemberProductLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey)
      throws Exception {
    Likes likes = null;
    try {
      session = sessionFactory.openSession();
      likes = likesDao.selectById(productAndMemberCompositeKey, session).orElse(null);
      return likes != null;
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception(e.getMessage());
    } finally {
      session.close();
    }
  }

  /**
   * 회원이 지정한 상품 찜 추가
   *
   * @param productAndMemberCompositeKey 상품 id, 회원 id로 이루어진 복합키
   * @return res : 추가된 찜 갯수
   * @throws Exception
   */
  @Override
  public int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    int res = 0;
    try {
      session = sessionFactory.openSession();
      res =
          likesDao.insert(
              new Likes(
                  productAndMemberCompositeKey.getMemberId(),
                  productAndMemberCompositeKey.getProductId()),
              session);
      session.commit();
    } catch (SQLException e) {
      session.rollback();
      e.printStackTrace();
      throw new LikesEntityNotFoundException();
    } catch (Exception e) {
      session.rollback();
    } finally {
      session.close();
    }
    return res;
  }

  /**
   * 회원이 지정한 상품 찜 삭제
   *
   * @param productAndMemberCompositeKey 상품 id, 회원 id로 이루어진 복합키
   * @return res : 삭제된 찜 갯수
   * @throws Exception
   */
  @Override
  public int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    int res = 0;
    try {
      session = sessionFactory.openSession();
      res = likesDao.deleteById(productAndMemberCompositeKey, session);
      session.commit();
    } catch (SQLException e) {

      session.rollback();
      e.printStackTrace();
      throw new LikesEntityNotFoundException();
    } catch (Exception e) {
      session.rollback();
    } finally {
      session.close();
    }
    return res;
  }

  /**
   * 회원의 찜 벌크 삭제
   *
   * @param keyList 리스트 : 상품 id, 회원 id로 이루어진 복합키
   * @return res : 삭제된 찜 갯수
   * @throws Exception
   */
  @Override
  public int removeSomeLikes(List<ProductAndMemberCompositeKey> keyList) {
    int res = 0;
    try {
      session = sessionFactory.openSession();
      for (ProductAndMemberCompositeKey key : keyList) {
        res = likesDao.deleteById(key, session) > 0 ? res + 1 : res;
      }
      session.commit();
    } catch (SQLException e) {

      session.rollback();
      e.printStackTrace();
      throw new LikesEntityNotFoundException();
    } catch (Exception e) {
      session.rollback();
    } finally {
      session.close();
    }
    return res;
  }
}
