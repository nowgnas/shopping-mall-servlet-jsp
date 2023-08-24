package app.dao.cart;

import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public interface DaoFrame<K, V> {

  int insert(V v, SqlSession session) throws Exception;

  int update(V v, SqlSession session) throws Exception;

  int deleteById(K k, SqlSession session) throws Exception;

  Optional<V> selectById(K k, SqlSession session) throws Exception;

  List<V> selectAll(SqlSession session) throws Exception;
}
