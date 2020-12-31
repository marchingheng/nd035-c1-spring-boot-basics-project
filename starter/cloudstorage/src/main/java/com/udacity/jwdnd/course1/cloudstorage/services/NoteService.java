package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public int createNote(Notes note){
        return notesMapper.insertNotes(note);
    }

    public Integer updateNote(Notes notes){
        return notesMapper.updateNote(notes);
    }

    public Integer deleteNote(Integer noteId){
        return notesMapper.deleteNote(noteId);
    }

    public List<Notes> getNotes(Integer userId){
        return notesMapper.getAllNotes(userId);
    }
}

