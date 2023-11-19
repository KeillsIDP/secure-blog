package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;

import java.util.Optional;

public interface PostService {
    Post getPostById(long id);
    boolean savePost(Post post, long userId);
    boolean updatePost(Post post);
}
