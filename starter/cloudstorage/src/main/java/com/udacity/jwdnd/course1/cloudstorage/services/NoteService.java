package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import java.util.List;

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
        return noteMapper.getAllNotes();
    }

    public void deleteById(Integer id){ noteMapper.delete(id); }
}
