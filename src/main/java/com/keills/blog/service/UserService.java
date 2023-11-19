package com.keills.blog.service;

import com.keills.blog.model.User;

import java.util.Optional;

public interface UserService {
    boolean saveUser(User user);
    User getUserByUserId(Long id);
    User getUserByUsername(String username);
}
