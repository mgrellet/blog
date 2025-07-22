package com.blog.services.impl;

import com.blog.domain.entities.Category;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> listCategories() {
    return categoryRepository.findAllWithPostCount();
  }

  @Transactional
  @Override
  public Category createCategory(Category category) {
    if(categoryRepository.existsByNameIgnoreCase(category.getName())){
      throw new IllegalArgumentException("Category name already exists");
    }

    return categoryRepository.save(category);
  }


}
