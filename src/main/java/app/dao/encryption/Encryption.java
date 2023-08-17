package app.dao.encryption;

import app.dao.DaoFrame;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class Encryption implements DaoFrame<Long, Encryption> {

    @Override
    public int insert(Encryption encryption, SqlSession session) throws SQLException {
        return 0;
    }

    @Override
    public int update(Encryption encryption, SqlSession session) throws SQLException {
        return 0;
    }

    @Override
    public int deleteById(Long aLong, SqlSession session) throws SQLException {
        return 0;
    }

    @Override
    public Optional<Encryption> selectById(Long aLong, SqlSession session) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Encryption> selectAll(SqlSession session) throws SQLException {
        return null;
    }
}
