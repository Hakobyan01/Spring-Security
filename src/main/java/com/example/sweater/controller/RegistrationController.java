package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.CaptchaResponseDto;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    public RestTemplate restTemplate;

    private static final String CAPTHCA_URL = "https://www.google.com/recaptcha/api/siteverify?secter=%s&response";

    @GetMapping("/registration")
    public String getRegistration(){
        return "/registration";
    }

    @PostMapping(value = "/registration")
    public String addUser(@RequestParam String confirmPassword,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model){
        Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
        //for recaptcha
        String url = String.format(CAPTHCA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.EMPTY_LIST, CaptchaResponseDto.class);
        if (!response.isSuccess()){
            errors.put("captchaError", "Fill captcha");
        }
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
            model.addAttribute("messageType", "success");
        }else {
            model.addAttribute("message", "Activatin code is not found");
            model.addAttribute("messageType", "danger");
        }

        return "/login";
    }
}
