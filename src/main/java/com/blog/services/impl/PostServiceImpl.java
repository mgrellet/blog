package com.blog.services.impl;

import com.blog.domain.PostStatus;
import com.blog.domain.entities.Category;
import com.blog.domain.entities.Post;
import com.blog.domain.entities.Tag;
import com.blog.repositories.PostRepository;
import com.blog.services.CategoryService;
import com.blog.services.PostService;
import com.blog.services.TagService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final CategoryService categoryService;
  private final TagService tagService;

  @Transactional(readOnly = true)
  @Override
  public List<Post> getAllPost(UUID categoryId, UUID tagId) {
    if (categoryId != null && tagId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      Tag tag = tagService.getTagById(tagId);
      return postRepository.findAllByPostStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED,
          category,
          tag);
    }

    if (categoryId != null) {
      Category category = categoryService.getCategoryById(categoryId);
      return postRepository.findAllByPostStatusAndCategory(PostStatus.PUBLISHED, category);
    }

    if (tagId != null) {
      Tag tag = tagService.getTagById(tagId);
      return postRepository.findAllByPostStatusAndTagsContaining(PostStatus.PUBLISHED, tag);
    }

    return postRepository.findAllByPostStatus(PostStatus.PUBLISHED);

  }
}
