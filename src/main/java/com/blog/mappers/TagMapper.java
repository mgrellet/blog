package com.blog.mappers;

import com.blog.domain.PostStatus;
import com.blog.domain.dtos.TagDto;
import com.blog.domain.entities.Post;
import com.blog.domain.entities.Tag;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

  @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
  TagDto toTagResponse(Tag tag);

  @Named("calculatePostCount")
  default Integer calculatePostCount(Set<Post> posts) {
    if (posts == null) {
      return 0;
    }
    return (int) posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus()))
        .count();
  }
}
