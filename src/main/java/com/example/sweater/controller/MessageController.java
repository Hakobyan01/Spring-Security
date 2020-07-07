package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MessageController {
    @Autowired
    public MessageRepository messageRepository;

    @Value("${upload.message.filepath}")
    public String uploadPath;

    @GetMapping("/message-page")
    public String getRegisert(@AuthenticationPrincipal User user,
                              Message message,
                              Model model){
        model.addAttribute("messages", messageRepository.findAll());

        return "/message-page";
    }

    @PostMapping(value = "/save-message")
    private String saveMessage(@AuthenticationPrincipal User user,
                               @Valid Message message,
                               BindingResult bindingResult,
                               Model model,
                               @RequestParam("file") MultipartFile file) throws IOException {
        message.setAuthor(user);
        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("messages", messageRepository.findAll());
            model.mergeAttributes(errorsMap);
            return "/message-page";
        }else {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFileName = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFileName));

                message.setFileName(resultFileName);
            }
            messageRepository.save(message);
        }
        return "redirect:/message-page";
    }

    @GetMapping(value = "/delete-message/{id}")
    private String deleteMessage(@PathVariable Long id){
        Message message = messageRepository.findById(id).get();
        File file = new File(uploadPath + "/" + message.getFileName());
        messageRepository.delete(message);
        file.delete();
        return "redirect:/message-page";
    }
}
