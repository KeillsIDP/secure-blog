package com.keills.blog.repository;

import com.keills.blog.model.Blog;
import com.keills.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogRepo extends JpaRepository<Blog,Long> {
    Optional<Blog> findByUser(User user);
}
