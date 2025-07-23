package com.blog.controllers;

import com.blog.domain.dtos.PostDto;
import com.blog.domain.entities.Post;
import com.blog.mappers.PostMapper;
import com.blog.services.PostService;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

  private PostService postService;
  private final PostMapper postMapper;

  @GetMapping
  public ResponseEntity<List<PostDto>> getAllPosts(
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId) {

    List<Post> posts = postService.getAllPost(categoryId, tagId);

    List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
    return ResponseEntity.ok(postDtos);
  }
}
