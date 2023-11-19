package com.keills.blog.controller;

import com.keills.blog.model.Blog;
import com.keills.blog.model.User;
import com.keills.blog.service.BlogService;
import com.keills.blog.service.UserService;
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
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public String userBlog(@PathVariable("id") String id, Model model){
        try{
            Blog blog = blogService.getBlogById(Long.parseLong(id));
            boolean isOwner = UserServiceImpl.isIdMatchesLoggedUser(blog.getUser().getId());

            model.addAttribute("isOwner",isOwner);
            model.addAttribute("blog",blog);

            return "blog";
        }
        catch (Exception e){
            return "error";
        }
    }

    @GetMapping("/{id}/redact")
    public String toBlogRedaction(@PathVariable("id") String id, Model model){
        try{
            Blog blog = blogService.getBlogById(Long.parseLong(id));
            boolean isOwner = UserServiceImpl.isIdMatchesLoggedUser(blog.getUser().getId());

            if(isOwner){
                model.addAttribute("blog",blog);
                return "redact-blog";
            }
            else
                return "redirect:/index";
        }
        catch (Exception e){
            return "error";
        }


    }

    @PostMapping("/{id}/redact")
    public String saveRedaction(@PathVariable("id") String id, @Valid @ModelAttribute("blog") Blog blog, BindingResult result, Model model){
        try{
            Blog blogFromDb = blogService.getBlogById(Long.parseLong(id));
            boolean isOwner = UserServiceImpl.isIdMatchesLoggedUser(blogFromDb.getUser().getId());

            if(isOwner){
                blogService.updateBlog(blog);
                model.addAttribute("blog",blog);
                return "redirect:/blog/"+id;
            }
            else
                return "redirect:/index";
        }
        catch(Exception e){
            return "error";
        }

    }
}
