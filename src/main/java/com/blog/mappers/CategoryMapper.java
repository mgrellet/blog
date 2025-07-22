package com.blog.mappers;

import com.blog.domain.PostStatus;
import com.blog.domain.dtos.CategoryDto;
import com.blog.domain.dtos.CreateCategoryRequest;
import com.blog.domain.entities.Category;
import com.blog.domain.entities.Post;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

  @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
  CategoryDto toDto(Category category);

  Category toEntity(CreateCategoryRequest dto);

  @Named("calculatePostCount")
  default long calculatePostCount(List<Post> posts) {
    if (null == posts) {
      return 0;
    }
    return posts.stream().filter(post -> PostStatus.PUBLISHED.equals(post.getPostStatus())).count();
  }


}
