package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private UserService userService;
    private NoteService noteService;

    public NotesController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping
    public String createOrUpdateNote(Authentication authentication, Model model, @ModelAttribute Notes note){
        Integer userId = this.userService.getUser(authentication.getName()).getUserId();
        note.setUserId(userId);

        if(note.getNoteId() != null && note.getNoteId() > 0) {
            try {
                noteService.updateNote(note);
                model.addAttribute("success", "Note updated!");
            } catch (Exception e) {
                model.addAttribute("error", "Cannot update note " + e.getMessage());
            }
        }else{
            try {
                noteService.createNote(note);
                model.addAttribute("success", "New note added!");
            } catch (Exception e) {
                model.addAttribute("error", "Cannot find note " + e.getMessage());
            }
        }

        return "result";
    }

    @GetMapping("/delete")
    public String deleteNote(Authentication authentication, Model model, Integer noteId){
        try {
            noteService.deleteNote(noteId);
            model.addAttribute("success", "Note removed!");
        } catch (Exception e) {
            model.addAttribute("error", "Cannot find note " + e.getMessage());
        }

        return "result";
    }
}

