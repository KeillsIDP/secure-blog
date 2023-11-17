package com.keills.blog.controller;

import com.keills.blog.model.Blog;
import com.keills.blog.model.User;
import com.keills.blog.service.BlogService;
import com.keills.blog.service.UserService;
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
    private UserService userService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/{id}")
    public String userBlog(@PathVariable("id") String id, Model model){
        User user = getUserById(id);
        if(user==null)
            return "error";

        Optional<Blog> blog = blogService.getBlog(user);
        if(!blog.isPresent())
            return "error";

        User userSecurity = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idSecurity = userSecurity.getId();

        boolean isOwner = idSecurity == user.getId();
        model.addAttribute("isOwner",isOwner);
        model.addAttribute("user_id",user.getId());
        model.addAttribute("blog",blog.get());
        return "blog";
    }

    @GetMapping("/{id}/redact")
    public String toBlogRedaction(@PathVariable("id") String id, Model model){
        User user = getUserById(id);
        if(user==null)
            return "error";

        User userSecurity = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idSecurity = userSecurity.getId();

        if(user.getId()==idSecurity)
            return getRedactTemplate(user,model);
        else
            return getRedactTemplate(userSecurity,model);
    }

    @PostMapping("/{id}/redact")
    public String saveRedaction(@PathVariable("id") String id, @Valid @ModelAttribute("blog") Blog blog, BindingResult result, Model model){
        User user = getUserById(id);
        if(user==null)
            return "error";

        User userSecurity = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idSecurity = userSecurity.getId();
        if(user.getId()==idSecurity){
            blogService.updateBlog(blog,user);
            model.addAttribute("blog",blog);
            return "redirect:/blog/"+id;
        }
        return "error";
    }

    private User getUserById(String id){
        Optional<User> response = userService.getUserByUserId(Long.parseLong(id));
        if(!response.isPresent())
            return null;

        User user = response.get();
        return user;
    }
    private String getRedactTemplate(User user,Model model){
        Optional<Blog> blog = blogService.getBlog(user);
        if(!blog.isPresent())
            return "error";
        model.addAttribute("user_id",user.getId());
        model.addAttribute("blog",blog.get());
        return "redact-blog";
    }
}
