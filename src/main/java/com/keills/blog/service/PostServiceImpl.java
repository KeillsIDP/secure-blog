package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;
import com.keills.blog.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepo postRepo;
    private final BlogService blogService;

    @Autowired
    public PostServiceImpl(PostRepo postRepo, BlogService blogService) {
        this.postRepo = postRepo;
        this.blogService = blogService;
    }

    @Override
    public Optional<Post> getPostById(String id) {
        return postRepo.findById(Long.parseLong(id));
    }

    @Override
    public boolean savePost(Post post, Blog blog) {
        Optional<Post> postFromDb = postRepo.findById(post.getId());
        if(postFromDb.isPresent())
            return false;

        post.setBlog(blog);
        postRepo.save(post);

        Set<Post> posts = blog.getPosts();
        posts.add(post);
        blog.setPosts(posts);

        blogService.updateBlog(blog, blog.getUser());

        return true;
    }
}
