package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String getRegistration(){
        return "/registration";
    }

    @PostMapping(value = "/registration")
    public String addUser(@RequestParam String confirmPassword,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model){
        Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        model.mergeAttributes(errors);
        if (!user.getPassword().equals(confirmPassword)){
            errors.put("confirmPasswordError", "Passwords are different!");
        }
        if (!errors.isEmpty()){
            model.mergeAttributes(errors);
            return "/registration";
        }else {
            if (!userService.addUser(user)) {
                model.addAttribute("userExistsError", "user exists!");
                return "/registration";
            }
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
