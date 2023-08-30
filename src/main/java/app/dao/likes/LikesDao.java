package app.dao.likes;

import app.dto.likes.request.LikesSelectForPage;
import app.entity.Likes;
import app.entity.ProductAndMemberCompositeKey;
import app.exception.likes.LikesEntityDuplicateException;
import app.exception.likes.LikesEntityNotFoundException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LikesDao implements LikesDaoFrame<ProductAndMemberCompositeKey, Likes> {

  private static LikesDao instance;

  private LikesDao() {}

  public static LikesDao getInstance() {
    if (instance == null) {
      instance = new LikesDao();
    }
    return instance;
  }

  @Override
  public int insert(Likes likes, SqlSession session) throws Exception {
      return session.insert("likes.insert", likes);
  }

  @Override
  public int update(Likes likes, SqlSession session) throws SQLException {
    return 0;
  }

  @Override
  public int deleteById(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, SqlSession session)
      throws SQLException {
    return session.delete("likes.delete", productAndMemberCompositeKey);
  }

  @Override
  public Optional<Likes> selectById(
      ProductAndMemberCompositeKey productAndMemberCompositeKey, SqlSession session)
      throws SQLException {
    Likes likes = session.selectOne("likes.select", productAndMemberCompositeKey);
    return Optional.ofNullable(likes);
  }

  @Override
  public List<Likes> selectAll(SqlSession session) throws SQLException {
    return null;
  }

  @Override
  public List<Long> selectAllProduct(LikesSelectForPage likesSelectForPage, SqlSession session)
      throws SQLException {
    List<Long> productIdList = session.selectList("likes.selectall", likesSelectForPage);
    if (productIdList.isEmpty()) throw new LikesEntityNotFoundException();
    return productIdList;
  }

  public Integer selectTotalPage(Long memberId, Integer perPage, SqlSession session) {
    Long totalCnt = session.selectOne("likes.selectTotalCount", memberId);
    return (int) Math.ceil((float) totalCnt / perPage);
  }
}
