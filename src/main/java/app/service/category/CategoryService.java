package app.service.category;

import app.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory() throws Exception;
}
