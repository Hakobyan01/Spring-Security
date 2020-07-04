package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistration(){
        return "/registration";
    }

    @PostMapping(value = "/saveUser")
    public String addUser(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          Model model){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        if (!userService.addUser(user)){
            model.addAttribute("message", "user exists!");
            return "/registration";
        }

        return "redirect:/login";
    }

    @GetMapping(value = "/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivate = userService.activateUser(code);

        if (isActivate){
            model.addAttribute("message", "User successfully activeted!");
        }else {
            model.addAttribute("message", "Activatin code is not found");
        }

        return "/login";
    }
}
