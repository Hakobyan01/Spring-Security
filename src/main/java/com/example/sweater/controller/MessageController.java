package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {
    @Autowired
    public MessageRepository messageRepository;

    @GetMapping("/message-page")
    public String getRegisert(@AuthenticationPrincipal User user,
                              Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "message-page";
    }

    @PostMapping(value = "/save-message")
    private String saveMessage(@RequestParam String message,
                                @AuthenticationPrincipal User user){
        Message msg = new Message(message, user);
        messageRepository.save(msg);

        return "redirect:/message-page";
    }

    @PostMapping(value = "/delete-message")
    private String deleteMessage(@RequestParam Long id){
        messageRepository.deleteById(id);

        return "redirect:/message-page";
    }
}
