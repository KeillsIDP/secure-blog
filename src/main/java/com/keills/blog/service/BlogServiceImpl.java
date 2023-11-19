package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.User;
import com.keills.blog.repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService{
    private final BlogRepo blogRepo;

    @Autowired
    public BlogServiceImpl(BlogRepo blogRepo) {
        this.blogRepo = blogRepo;
    }

    @Override
    public Blog getBlogByUser(User user) {
        Optional<Blog> response = blogRepo.findByUser(user);
        if(!response.isPresent())
            throw new RuntimeException("No blog found");

        return response.get();
    }

    @Override
    public Blog getBlogById(long id) {
        Optional<Blog> response = blogRepo.findById(id);
        if(!response.isPresent())
            throw new RuntimeException("No blog found");

        return response.get();
    }

    @Override
    public boolean saveBlog(Blog blog) {
        Optional<Blog> response = blogRepo.findById(blog.getId());
        if(response.isPresent())
            return false;

        blogRepo.save(blog);
        return true;
    }

    @Override
    public void updateBlog(Blog blog) {
        Blog blogFromDb = getBlogById(blog.getId());

        blogFromDb.setBlogInfo(blog.getBlogInfo());
        blogFromDb.setBlogName(blog.getBlogName());
        blogFromDb.setPosts(blog.getPosts());

        blogRepo.save(blogFromDb);
    }
}
