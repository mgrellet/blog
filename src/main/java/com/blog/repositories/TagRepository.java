package com.blog.repositories;

import com.blog.domain.entities.Tag;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
  @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.posts")
  List<Tag> finAllWithPostCount();

  List<Tag> findByNameIn(Set<String> names);
}
