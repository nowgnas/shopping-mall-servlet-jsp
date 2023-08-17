package app.dao;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.entity.Likes;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class LikesDao implements LikesDaoFrame<ProductAndMemberCompositeKey, Likes> {

  @Override
  public int insert(Likes likes, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int update(Likes likes, SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public int deleteById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws Exception {
    return 0;
  }

  @Override
  public Optional<Likes> selectById(ProductAndMemberCompositeKey productAndMemberCompositeKey,
      SqlSession session) throws Exception {
    return Optional.empty();
  }

  @Override
  public List<Likes> selectAll(SqlSession session) throws Exception {
    return null;
  }

  @Override
  public List<Likes> selectAll(Long memberId, SqlSession session) throws Exception {
    return null;
  }
}
