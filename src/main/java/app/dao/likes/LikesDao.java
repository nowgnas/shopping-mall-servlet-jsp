package app.dao.likes;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Likes;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.error.CustomException;
import app.error.ErrorCode;
import org.apache.ibatis.session.SqlSession;

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
  public int insert(Likes likes, SqlSession session) throws SQLException {
    return session.insert("likes.insert", likes);
  }

  @Override
  public int update(Likes likes, SqlSession session) throws SQLException {
    return 0;
  }

  @Override
  public int deleteById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws SQLException {
    return session.delete("likes.delete", productAndMemberCompositeKey);
  }

  @Override
  public Optional<Likes> selectById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws SQLException {
    Likes likes = session.selectOne("likes.select", productAndMemberCompositeKey);
    if (likes == null) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return Optional.ofNullable(likes);
  }

  @Override
  public List<Likes> selectAll(SqlSession session) throws SQLException {
    return null;
  }

  @Override
  public List<Long> selectAllProduct(Long memberId, SqlSession session) throws SQLException {
    List<Long> productIdList = session.selectList("likes.selectall", memberId);
    if (productIdList.isEmpty()) throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
    return productIdList;
  }
}
