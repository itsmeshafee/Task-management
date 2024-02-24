package com.project.usertask.project1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.usertask.project1.entities.User;
import com.project.usertask.project1.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home - Task Management");
        return "home";
    }

    @GetMapping("/signin")
    public String login(@ModelAttribute User user ,Model model){
        model.addAttribute("title", "Login - Task Management");
        return "signin";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register - Task Management");
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String registerUser(
                            @Valid 
                            @ModelAttribute User user,
                            BindingResult bindingResult,
                            Model model,
                            HttpSession session ){
        user.setRole("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        model.addAttribute("user", new User());
        return "signup";
    }
    
    @GetMapping("/login-fail")
    @ResponseBody
    public String loginFail(){
        return "Invalid Username and Password";
    }
}
