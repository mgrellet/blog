package com.blog.services.impl;


import com.blog.domain.entities.Tag;
import com.blog.repositories.TagRepository;
import com.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  @Override
  public List<Tag> getTags() {
    return tagRepository.finAllWithPostCount();
  }

  @Transactional
  @Override
  public List<Tag> createTags(Set<String> tagNames) {
    List<Tag> existingTags = tagRepository.findByNameIn(tagNames);

    Set<String> existingTagNames = existingTags.stream().map(Tag::getName)
        .collect(Collectors.toSet());

    List<Tag> newTags = tagNames.stream()
        .filter(tagName -> !existingTagNames.contains(tagName))
        .map(name ->
            Tag.builder()
                .name(name)
                .posts(new HashSet<>())
                .build())
        .toList();

    List<Tag> savedTags = new ArrayList<>();
    if (!newTags.isEmpty()) {
      savedTags = tagRepository.saveAll(newTags);
    }
    savedTags.addAll(existingTags);
    return savedTags;
  }

  @Override
  public void deleteTag(UUID id) {
    tagRepository.findById(id).ifPresent(tag -> {
      if (!tag.getPosts().isEmpty()) {
        throw new IllegalStateException("Cannot delete tag with posts");
      }
      tagRepository.deleteById(id);
    });
  }

  @Override
  public Tag getTagById(UUID id) {
    return tagRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No tag found with id: " + id));

  }

  @Override
  public List<Tag> getTagByIds(Set<UUID> tagIds) {
    List<Tag> tags = tagRepository.findAllById(tagIds);

    if(tags.size() != tagIds.size()) {
      throw new EntityNotFoundException("Not all specified tag ids exists");
    }

    return tags;
  }

}
