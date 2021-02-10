package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService) {
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public int add(Note note) {
        return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userService.getUserId()));
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = noteMapper.getAllNotes(userService.getUserId());
        if (noteList != null)
            return noteList;
        else
            return new ArrayList<Note>();
    }

    public void deleteById(Integer id){ noteMapper.delete(id); }
}
