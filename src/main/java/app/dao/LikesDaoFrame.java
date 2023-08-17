package app.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;

public interface LikesDaoFrame<K, V> extends DaoFrame<K, V> {
  List<V> selectAll(Long memberId, SqlSession session) throws Exception;
}
