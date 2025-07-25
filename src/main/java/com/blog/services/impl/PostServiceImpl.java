package com.blog.services.impl;

import com.blog.domain.CreatePostRequest;
import com.blog.domain.PostStatus;
import com.blog.domain.entities.Category;
import com.blog.domain.entities.Post;
import com.blog.domain.entities.Tag;
import com.blog.domain.entities.User;
import com.blog.repositories.PostRepository;
import com.blog.services.CategoryService;
import com.blog.services.PostService;
import com.blog.services.TagService;
import java.util.HashSet;
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

  private static final int WORDS_PER_MINUTE = 200;

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

  @Override
  public List<Post> getDraftPost(User user) {
    return postRepository.findAllByAuthorAndPostStatus(user, PostStatus.DRAFT);
  }

  @Transactional
  @Override
  public Post createPost(User user, CreatePostRequest createPostRequest) {

    Post newPost = new Post();
    newPost.setTitle(createPostRequest.getTitle());
    newPost.setContent(createPostRequest.getContent());
    newPost.setPostStatus(createPostRequest.getStatus());
    newPost.setAuthor(user);
    newPost.setReadingTime(calculateTeReadingTime(createPostRequest.getContent()));

    Category category = categoryService.getCategoryById(createPostRequest.getCategoryId());
    newPost.setCategory(category);

    List<Tag> tags = tagService.getTagByIds(createPostRequest.getTagIds());
    newPost.setTags(new HashSet<>(tags));
    return postRepository.save(newPost);
  }

  private Integer calculateTeReadingTime(String content) {
    if (content == null || content.isEmpty()) {
      return 0;
    }

    int wordCount = content.trim().split("\\s+").length;
    return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);

  }
}
