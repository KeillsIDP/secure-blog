package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;

import java.util.Optional;

public interface PostService {
    Optional<Post> getPostById(String id);

    boolean savePost(Post post, Blog blog);
}
