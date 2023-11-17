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
    public Optional<Blog> getBlog(User user) {
        return blogRepo.findByUser(user);
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
    public void updateBlog(Blog blog,User user) {
        Optional<Blog> response = getBlog(user);
        if(!response.isPresent())
            return;

        Blog oldBlog = response.get();
        oldBlog.setBlog_info(blog.getBlog_info());
        oldBlog.setBlog_name(blog.getBlog_name());
        oldBlog.setPosts(blog.getPosts());
        blogRepo.save(oldBlog);
    }
}
