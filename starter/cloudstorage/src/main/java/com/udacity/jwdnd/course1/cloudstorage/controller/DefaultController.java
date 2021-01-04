package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    @Autowired
    private NoteService notesService;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialService credentialsService;
    private User user;
    @Autowired
    private FileService filesService;

    @RequestMapping("/*")
    public String fallbackUrl(Model model)
    {
        model.addAttribute("error","URL Not Found");
        setModelAttributes(model);
        return "result";
    }

    public void setModelAttributes(Model model)
    {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                user=userService.getUser(authentication.getName());
            }
            model.addAttribute("newNote",new Notes());
            model.addAttribute("newCredential",new Credential());
            model.addAttribute("Notes",notesService.getNotes(user.getUserId()));
            model.addAttribute("credentials",credentialsService.getCredentials(user.getUserId()));
            model.addAttribute("newFiles",filesService.getFiles(user.getUserId()));
        }
        catch (Exception e)
        {
            System.out.println(" msg "+e);
        }

    }
}

