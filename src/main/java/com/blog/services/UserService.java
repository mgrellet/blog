package com.blog.services;

import com.blog.domain.entities.User;
import java.util.UUID;

public interface UserService {

  User getUserById(UUID id);

}
