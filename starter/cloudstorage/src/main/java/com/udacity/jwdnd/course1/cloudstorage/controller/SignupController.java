package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String SignupView(){
        return "signup";
    }

    @PostMapping
    public RedirectView signupUser(@ModelAttribute User user, Model model, RedirectAttributes redir){
        String signupError = null;

        if(!userService.isUsernameAvailable(user.getUsername())){
            signupError = "Username already Exists!";
        }

        if(signupError == null){
            int rowAdded = userService.createUser(user);
            if(rowAdded < 0){
                signupError = "There was an error signing you up. Please Try Again.";
            }
        }

        if(signupError == null){
            model.addAttribute("signupSuccess", true);
            RedirectView redirectView= new RedirectView("/login",true);
            redir.addFlashAttribute("signupSuccess",true);
            return redirectView;
        }else{
            model.addAttribute("signupError", signupError);
            return null;
        }
    }
}
