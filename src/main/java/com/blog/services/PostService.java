package com.blog.services;

import com.blog.domain.entities.Post;
import java.util.List;
import java.util.UUID;

public interface PostService {

  List<Post> getAllPost(UUID categoryId, UUID tagId);

}
