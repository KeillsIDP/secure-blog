package com.keills.blog.service;

import com.keills.blog.model.User;

import java.util.Optional;

public interface UserService {
    boolean saveUser(User user);
    Optional<User> getUserByUserId(Long id);
    Optional<User> getUserByUserName(String username);
}
