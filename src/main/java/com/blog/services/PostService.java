package com.blog.services;

import com.blog.domain.CreatePostRequest;
import com.blog.domain.UpdatePostRequest;
import com.blog.domain.entities.Post;
import com.blog.domain.entities.User;
import java.util.List;
import java.util.UUID;

public interface PostService {

  List<Post> getAllPost(UUID categoryId, UUID tagId);

  List<Post> getDraftPost(User user);

  Post createPost(User user, CreatePostRequest createPostRequest);

  Post updatePost(UUID id, UpdatePostRequest updatePostRequest);

}
