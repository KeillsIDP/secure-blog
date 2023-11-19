package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.User;

import java.util.Optional;

public interface BlogService {

    Blog getBlogByUser(User user);
    Blog getBlogById(long id);
    boolean saveBlog(Blog blog);
    void updateBlog(Blog blog);
}
