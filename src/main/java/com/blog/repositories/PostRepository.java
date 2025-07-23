package com.blog.repositories;

import com.blog.domain.PostStatus;
import com.blog.domain.entities.Category;
import com.blog.domain.entities.Post;
import com.blog.domain.entities.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, UUID> {

  List<Post> findAllByPostStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);

  List<Post> findAllByPostStatusAndCategory(PostStatus status, Category category);

  List<Post> findAllByPostStatusAndTagsContaining(PostStatus status, Tag tag);

  List<Post> findAllByPostStatus(PostStatus status);


}
