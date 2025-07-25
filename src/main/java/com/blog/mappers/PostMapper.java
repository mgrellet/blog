package com.blog.mappers;

import com.blog.domain.CreatePostRequest;
import com.blog.domain.UpdatePostRequest;
import com.blog.domain.dtos.CreatePostRequestDto;
import com.blog.domain.dtos.PostDto;
import com.blog.domain.dtos.UpdatePostRequestDto;
import com.blog.domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

  @Mapping(target="author", source="author")
  @Mapping(target="category", source="category")
  @Mapping(target="tags", source="tags")
  @Mapping(target="postStatus", source = "postStatus")
  PostDto toDto(Post post);

  @Mapping(target = "tagIds", source = "tagIds")
  CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);

  UpdatePostRequest toUpdatePostRequest(UpdatePostRequestDto dto);

}
