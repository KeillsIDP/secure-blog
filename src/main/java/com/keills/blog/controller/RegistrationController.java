package com.keills.blog.controller;

import com.keills.blog.model.User;
import com.keills.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String showRegistrationForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/save")
    public String registrateUser(@Valid @ModelAttribute("user") User user, BindingResult result,Model model){
        Optional<User> userFromDb = userService.getUserByUserName(user.getUsername());
        if(userFromDb.isPresent())
            result.reject("user already exists");

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/registration";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }
}
