package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.User;

import java.util.Optional;

public interface BlogService {

    Optional<Blog> getBlog(User user);
    boolean saveBlog(Blog blog);
    void updateBlog(Blog blog,User user);
}
