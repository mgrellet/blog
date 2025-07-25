package com.blog.controllers;

import com.blog.domain.CreatePostRequest;
import com.blog.domain.UpdatePostRequest;
import com.blog.domain.dtos.CreatePostRequestDto;
import com.blog.domain.dtos.PostDto;
import com.blog.domain.dtos.UpdatePostRequestDto;
import com.blog.domain.entities.Post;
import com.blog.domain.entities.User;
import com.blog.mappers.PostMapper;
import com.blog.services.PostService;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

  private final PostService postService;
  private final UserService userService;
  private final PostMapper postMapper;


  @GetMapping
  public ResponseEntity<List<PostDto>> getAllPosts(
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId) {

    List<Post> posts = postService.getAllPost(categoryId, tagId);

    List<PostDto> postDtos = posts.stream().map(postMapper::toDto).toList();
    return ResponseEntity.ok(postDtos);
  }

  @GetMapping("/drafts")
  public ResponseEntity<List<PostDto>> getDraftPosts(@RequestAttribute UUID userId) {
    User loggedInUser = userService.getUserById(userId);

    List<PostDto> draftPostDtos = postService.getDraftPost(loggedInUser).stream()
        .map(postMapper::toDto).toList();

    return ResponseEntity.ok(draftPostDtos);
  }

  @PostMapping
  public ResponseEntity<PostDto> createPost(
      @Valid @RequestBody CreatePostRequestDto createPostRequestDto,
      @RequestAttribute UUID userId
  ) {

    User loggedInUser = userService.getUserById(userId);
    CreatePostRequest createPostRequest = postMapper.toCreatePostRequest(createPostRequestDto);
    Post postCreated = postService.createPost(loggedInUser, createPostRequest);
    PostDto createdPostDto = postMapper.toDto(postCreated);

    return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostDto> updatePost(@PathVariable UUID id,
      @Valid @RequestBody UpdatePostRequestDto updatePostRequestDto) {

    UpdatePostRequest updatePostRequest = postMapper.toUpdatePostRequest(updatePostRequestDto);

    Post updatedPost = postService.updatePost(id, updatePostRequest);

    PostDto updatedPostDto = postMapper.toDto(updatedPost);

    return ResponseEntity.ok(updatedPostDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDto> getPost(@PathVariable UUID id) {

    Post post = postService.getPostById(id);
    PostDto postDto = postMapper.toDto(post);
    return ResponseEntity.ok(postDto);
  }
}
