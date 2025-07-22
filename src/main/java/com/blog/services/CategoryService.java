package com.blog.services;

import com.blog.domain.entities.Category;
import java.util.List;

public interface CategoryService {

  List<Category> listCategories();

  Category createCategory(Category category);
}
