package com.keills.blog.controller;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;
import com.keills.blog.model.User;
import com.keills.blog.service.BlogService;
import com.keills.blog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/post")
public class PostController {
    //post service
    @Autowired
    private PostService postService;
    @Autowired
    private BlogService blogService;

    //get post by id
    @GetMapping("/{id}")
    public String getPost(@PathVariable(name = "id") String postId, Model model){
        Optional<Post> postFromDb = postService.getPostById(postId);
        if(!postFromDb.isPresent())
            return "error";

        Post post = postFromDb.get();
        model.addAttribute("post",post);
        return "post";
    }

    //need blog id
    @GetMapping("/create")
    public String createPost(Model model){
        model.addAttribute("post",new Post());
        return "post-create";
    }

    //save post
    @PostMapping("/create")
    public String saveCreatedPost(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
        User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Blog> blog = blogService.getBlog(user);

        if(!blog.isPresent())
            return "error";

        postService.savePost(post,blog.get());
        model.addAttribute("post",post);
        return "post";
    }
//
//    // get post by id show in form if not owner go to own blog
//    @GetMapping("/{id}/redact")
//    public String redactPost(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
//
//    }
//
//    // save post
//    @PostMapping("/{id}/redact")
//    public String saveRedactedPost(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
//
//    }
}
