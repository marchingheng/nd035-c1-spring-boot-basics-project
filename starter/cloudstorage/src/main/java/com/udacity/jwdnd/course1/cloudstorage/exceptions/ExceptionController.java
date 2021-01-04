package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionController  implements HandlerExceptionResolver {
    @Autowired
    private NoteService notesService;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialService credentialsService;

    private User user;
    @Autowired
    private FileService filesService;


/**
 the defaultcontroller and error.html will take care of the page not found error
 **/
//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public String notFoundExceptionHandle(Model model)
//    {
//        model.addAttribute("error","URL Not Found");
//        setModelAttributes(model);
//        return "result";
//    }

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

    public void setModelAndView(ModelAndView modelAndView){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                user=userService.getUser(authentication.getName());
            }
            modelAndView.addObject("newNote",new Notes());
            modelAndView.addObject("newCredential",new Credential());
            modelAndView.addObject("Notes",notesService.getNotes(user.getUserId()));
            modelAndView.addObject("credentials",credentialsService.getCredentials(user.getUserId()));
            modelAndView.addObject("newFiles",filesService.getFiles(user.getUserId()));
        }
        catch (Exception e)
        {
            System.out.println(" msg "+e);
        }
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        ModelAndView modelAndView = new ModelAndView();

        if(e instanceof MaxUploadSizeExceededException){
            modelAndView = new ModelAndView();
            setModelAndView(modelAndView);
            modelAndView.addObject("error", "File too large to upload");
            modelAndView.setViewName("result.html");
            return modelAndView;
        }
        modelAndView.setViewName("result.html");
        return modelAndView;
    }
}

