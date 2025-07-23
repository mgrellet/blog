package com.blog.controllers;

import com.blog.domain.dtos.CreateTagsRequest;
import com.blog.domain.dtos.TagDto;
import com.blog.domain.entities.Tag;
import com.blog.mappers.TagMapper;
import com.blog.services.TagService;
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
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

  private final TagService tagService;
  private final TagMapper tagMapper;

  @GetMapping
  public ResponseEntity<List<TagDto>> getAllTags() {
    List<Tag> tags = tagService.getTags();

    List<TagDto> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
    return ResponseEntity.ok(tagResponses);
  }

  @PostMapping
  public ResponseEntity<List<TagDto>> createTags(
      @RequestBody CreateTagsRequest createTagsRequest) {

    List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());

    List<TagDto> createdTagResponses = savedTags.stream().map(tagMapper::toTagResponse).toList();
    return new ResponseEntity<>(createdTagResponses, HttpStatus.CREATED);

  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
    tagService.deleteTag(id);
    return ResponseEntity.noContent().build();
  }

}
