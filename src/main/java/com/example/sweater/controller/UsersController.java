package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getUsersList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }

    @GetMapping("edit/{user}")
    public String userEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("edit-user")
    public  String userChangesSave(@RequestParam("userid") User user,
                            @RequestParam String username,
                            @RequestParam String password,
                            @RequestParam Map<String, String> form,
                            Model model){
        user.setUsername(username==null ? user.getUsername() : username);
        user.setPassword(password==null ? user.getPassword() : password);
        user.getRoles().clear();

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        for (String key : form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepository.save(user);
        return "redirect:/users";
    }
}
