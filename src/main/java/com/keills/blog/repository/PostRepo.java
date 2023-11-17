package com.keills.blog.repository;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepo extends JpaRepository<Post,Long> {
    Optional<Post> findByBlog(Blog blog);
}
