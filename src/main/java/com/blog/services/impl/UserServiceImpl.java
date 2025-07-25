package com.blog.services.impl;

import com.blog.domain.entities.User;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public User getUserById(UUID id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
  }
}
