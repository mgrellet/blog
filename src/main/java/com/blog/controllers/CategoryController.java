package com.blog.controllers;

import com.blog.domain.dtos.CategoryDto;
import com.blog.domain.dtos.CreateCategoryRequest;
import com.blog.domain.entities.Category;
import com.blog.mappers.CategoryMapper;
import com.blog.services.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  private final CategoryMapper categoryMapper;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> listCategories() {
    List<CategoryDto> categories = categoryService.listCategories()
        .stream().map(categoryMapper::toDto)
        .toList();
    return ResponseEntity.ok(categories);
  }

  @PostMapping
  public ResponseEntity<CategoryDto> createCategory(
      @Valid @RequestBody CreateCategoryRequest request) {

    Category categoryToCreate = categoryMapper.toEntity(request);
    Category savedCategory = categoryService.createCategory(categoryToCreate);
    return new ResponseEntity<>(
        categoryMapper.toDto(savedCategory),
        HttpStatus.CREATED
    );
  }


  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.noContent().build();
  }
}
