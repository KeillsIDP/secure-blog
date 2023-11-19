package com.keills.blog.service;

import com.keills.blog.model.Blog;
import com.keills.blog.model.Post;
import com.keills.blog.model.Role;
import com.keills.blog.model.User;
import com.keills.blog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final String USER_NOT_FOUND = "User with name %s not found";
    private final String USER_ALREADY_EXISTS = "User with name %s already exists";
    private final UserRepo userRepo;
    private final BlogService blogService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, BlogService blogService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.blogService = blogService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND,username)));
    }

    @Override
    public boolean saveUser(User user){
        String username = user.getUsername();
        Optional<User> response = userRepo.findByUsername(username);
        if(response.isPresent())
            return false;

        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        // blog init
        Blog userBlog = new Blog();

        userBlog.setUser(user);
        userBlog.setBlogName(username + " blog");
        userBlog.setBlogInfo("Nothing here");
        userBlog.setPosts(new HashSet<Post>());

        if(!blogService.saveBlog(userBlog))
            return false;

        user.setBlog(userBlog);
        userRepo.save(user);
        return true;
    }

    @Override
    public User getUserByUserId(Long id) {
        Optional<User> response = userRepo.findById(id);
        if(!response.isPresent())
            throw new RuntimeException("User not found");
        return response.get();
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> response = userRepo.findByUsername(username);
        if(!response.isPresent())
            throw new RuntimeException("User not found");
        return response.get();
    }

    public static boolean isIdMatchesLoggedUser(Long id){
        User userSecurity = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idSecurity = userSecurity.getId();

        return idSecurity == id;
    }

    public static long getLoggedUserId(){
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
