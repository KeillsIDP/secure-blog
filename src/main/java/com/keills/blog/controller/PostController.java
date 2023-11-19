package com.keills.blog.controller;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;
import com.keills.blog.model.User;
import com.keills.blog.service.BlogService;
import com.keills.blog.service.PostService;
import com.keills.blog.service.UserServiceImpl;
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

    //get post by id
    @GetMapping("/{id}")
    public String getPost(@PathVariable(name = "id") String postId, Model model){
        try{
            Post postFromDb = postService.getPostById(Long.parseLong(postId));
            Blog blog = postFromDb.getBlog();

            model.addAttribute("post",postFromDb);
            model.addAttribute("blogId",blog.getId());
            model.addAttribute("isOwner",UserServiceImpl.isIdMatchesLoggedUser(blog.getUser().getId()));
            return "post";
        }
        catch (Exception e){
            return "error";
        }

    }

    //need blog id
    @GetMapping("/create")
    public String createPost(Model model){
        try{
            long userId = UserServiceImpl.getLoggedUserId();

            model.addAttribute("user_id",userId);
            model.addAttribute("post",new Post());
            return "post-create";
        }
        catch (Exception e){
            return "error";
        }
    }

    //save post
    @PostMapping("/create/{userId}")
    public String saveCreatedPost(@PathVariable String userId, @Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
        try{
            postService.savePost(post,Long.parseLong(userId));
            model.addAttribute("post",post);
            return "redirect:/post/"+post.getId();
        }
        catch (Exception e){
            return "error";
        }

    }

    // get post by id show in form if not owner go to own blog
    @GetMapping("/{id}/redact")
    public String redactPost(@PathVariable(name = "id") String postId, Model model){
        try{
            Post postFromDb = postService.getPostById(Long.parseLong(postId));
            long postOwnerId = postFromDb.getBlog().getUser().getId();

            if(!UserServiceImpl.isIdMatchesLoggedUser(postOwnerId))
                return "redirect:/index";
            model.addAttribute("post",postFromDb);
            return "redact-post";
        }
        catch (Exception e){
            return "error";
        }
    }

    // save post
    @PostMapping("/{id}/redact")
    public String saveRedactedPost(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
        try {
            //check if post present
            Post postFromDb = postService.getPostById(post.getId());
            long postOwnerId = postFromDb.getBlog().getUser().getId();

            if(!UserServiceImpl.isIdMatchesLoggedUser(postOwnerId))
                return "redirect:/blog";

            if (result.hasErrors())
                return "error";

            postService.updatePost(post);
            return "redirect:/post/" + post.getId();
        }
        catch (Exception e){
            return "error";
        }
    }
}
