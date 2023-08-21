package app.dao.category;

import app.entity.Category;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;

public class CategoryDao implements CategoryDaoFrame<Long, Category> {

    @Override
    public int insert(Category category, SqlSession session) throws Exception {
    return 0;
    }

    @Override
    public int update(Category category, SqlSession session) throws Exception {
        return 0;
    }

    @Override
    public int deleteById(Long aLong, SqlSession session) throws Exception {
        return 0;
    }

    @Override
    public Optional<Category> selectById(Long aLong, SqlSession session) throws Exception {
        return Optional.empty();
    }

    @Override
    public List<Category> selectAll(SqlSession session) throws Exception {
        return null;
    }
}
