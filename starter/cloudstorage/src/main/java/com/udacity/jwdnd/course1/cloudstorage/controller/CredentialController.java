package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private CredentialService credentialService;
    private UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping
    public String createOrUpdateCredential(Authentication authentication, Model model, @ModelAttribute Credential credential) {
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        credential.setUserId(userId);

        if (credential.getCredentialId() != null && credential.getCredentialId() > 0){
            try {
                credentialService.updateCredential(credential);
                model.addAttribute("success", "Credential updated!");
            } catch (Exception e) {
                model.addAttribute("error", "Cannot update credential " + e.getMessage());
            }
        }else{
            try {
                credentialService.createCredential(credential);
                model.addAttribute("success", "New credential added!");
            } catch (Exception e) {
                model.addAttribute("error", "Cannot find credential " + e.getMessage());
            }

        }
        return "result";
    }

    @GetMapping("/delete")
    public String deleteCredential(Authentication authentication, Model model, Integer credentialId){
        try {
            credentialService.deleteCredential(credentialId);
            model.addAttribute("success", "Credential removed!");
        } catch (Exception e) {
            model.addAttribute("error", "Cannot find credential " + e.getMessage());
        }

        return "result";
    }
}

