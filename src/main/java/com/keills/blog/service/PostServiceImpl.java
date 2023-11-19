package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;
import com.keills.blog.model.User;
import com.keills.blog.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService{
    private final PostRepo postRepo;
    private final BlogService blogService;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepo postRepo, BlogService blogService, UserService userService) {
        this.postRepo = postRepo;
        this.blogService = blogService;
        this.userService = userService;
    }

    @Override
    public Post getPostById(long id) {
        Optional<Post> response = postRepo.findById(id);
        if(!response.isPresent())
            throw new RuntimeException("Post not found");
        return response.get();
    }

    @Override
    public boolean savePost(Post post, long userId) {
        try {
            User user = userService.getUserByUserId(userId);
            Blog blog = blogService.getBlogByUser(user);

            post.setBlog(blog);
            postRepo.save(post);

            Set<Post> posts = blog.getPosts();
            posts.add(post);
            blog.setPosts(posts);

            blogService.updateBlog(blog);

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updatePost(Post post) {
        try {
            Post postFromDb = getPostById(post.getId());
            postFromDb.setPostName(post.getPostName());
            postFromDb.setText(post.getText());
            postRepo.save(postFromDb);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
