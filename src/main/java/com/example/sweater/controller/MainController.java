package com.example.sweater.controller;

import com.example.sweater.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getMain(Model model){
        return "home";
    }

    @GetMapping("/your-profile")
    public String getYourProfile(Model model){
        return "your-profile";
    }
}