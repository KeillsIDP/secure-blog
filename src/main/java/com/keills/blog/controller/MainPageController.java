package com.keills.blog.controller;

import com.keills.blog.model.User;
import com.keills.blog.service.UserServiceImpl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    @GetMapping("")
    public String toIndex(){
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/toBlog")
    public String toUserBlog(Model model) {
        return "redirect:/blog/"+ UserServiceImpl.getLoggedUserId();
    }
}
