package app.service.likes;

import app.dao.cart.DaoFrame;
import app.dao.likes.LikesDao;
import app.dao.likes.LikesDaoFrame;
import app.entity.Likes;
import app.entity.Product;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.CustomException;
import app.exception.ErrorCode;
import app.utils.GetSessionFactory;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class ProductLikesService implements LikesService {

  LikesDaoFrame<ProductAndMemberCompositeKey, Likes> likesDao;
  DaoFrame<Long, Product> productDao;
  SqlSession session;

  public ProductLikesService() {
    likesDao = new LikesDao();
    //    productDao = new ProductDao();
  }

  //  @Override
  //  public List<MemberLikesResponseDto> getMemberLikes(Long memberId) {
  //    List<MemberLikesResponseDto> memberLikesList = new ArrayList<>();
  //    try {
  //      session = GetSessionFactory.getInstance().openSession();
  //      List<Likes> likesList = likesDao.selectAll(memberId, session);
  //
  //      long idx = 1;
  //      for (Likes likes : likesList) {
  //        // 추후에 변경
  //        //        MemberLikesResponseDto tmp = productDao.selectDtoById(likes.getProductId(),
  // session);
  //        MemberLikesResponseDto tmp = MemberLikesResponseDto.builder()
  //            .productId(idx++)
  //            .productName("tmp name")
  //            .productPrice(10000L)
  //            .imgUrl("url")
  //            .build();
  //
  //        memberLikesList.add(tmp);
  //      }
  //
  //    } catch (Exception e) {
  //
  //      e.printStackTrace();
  //      throw new CustomException(null);
  //    }
  //    finally {
  //      session.close();
  //    }
  //    return memberLikesList;
  //  }

  @Override
  public boolean getMemberProductLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    Likes likes = null;
    try {
      session = GetSessionFactory.getInstance().openSession();
      likes = likesDao.selectById(productAndMemberCompositeKey, session).orElse(null);
    } catch (SQLException e) {

      e.printStackTrace();
      throw new CustomException(ErrorCode.PRODUCT_IS_NOT_VALID);
    } catch (Exception e) {
      //

    } finally {
      session.close();
    }

    return likes != null;
  }

  @Override
  public int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    int res = 0;
    try {
      session = GetSessionFactory.getInstance().openSession();
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
      throw new CustomException(ErrorCode.PRODUCT_IS_NOT_VALID);
    } catch (Exception e) {
      //
      session.rollback();
    } finally {
      session.close();
    }
    return res;
  }

  @Override
  public int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    int res = 0;
    try {
      session = GetSessionFactory.getInstance().openSession();
      res = likesDao.deleteById(productAndMemberCompositeKey, session);
      session.commit();
    } catch (SQLException e) {

      session.rollback();
      e.printStackTrace();
      throw new CustomException(ErrorCode.PRODUCT_IS_NOT_VALID);
    } catch (Exception e) {
      session.rollback();
    } finally {
      session.close();
    }
    return res;
  }

  @Override
  public int removeSomeLikes(List<ProductAndMemberCompositeKey> keyList) {
    int res = 0;
    try {
      session = GetSessionFactory.getInstance().openSession();
      for (ProductAndMemberCompositeKey key : keyList) {
        res = likesDao.deleteById(key, session) > 0 ? res + 1 : res;
      }
      session.commit();
    } catch (SQLException e) {

      session.rollback();
      e.printStackTrace();
      throw new CustomException(ErrorCode.PRODUCT_IS_NOT_VALID);
    } catch (Exception e) {
      session.rollback();
    } finally {
      session.close();
    }
    return res;
  }
}
