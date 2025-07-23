package com.blog.services.impl;

import com.blog.domain.entities.Category;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

  @Override
  public void deleteCategory(UUID id) {
    Optional<Category> category = categoryRepository.findById(id);
    if(category.isPresent()){
      if(!category.get().getPosts().isEmpty()){
        throw new IllegalStateException("Category has posts associated with it");
      }
      categoryRepository.deleteById(id);
    }
  }

  @Override
  public Category getCategoryById(UUID id) {
    return categoryRepository.findById(id)
        .orElseThrow(()-> new EntityNotFoundException("Category not found with id" + id));
  }


}
