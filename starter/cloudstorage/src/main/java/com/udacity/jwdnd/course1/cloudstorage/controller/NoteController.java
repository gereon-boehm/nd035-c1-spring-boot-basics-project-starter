package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class NoteController {
    private final NoteService noteService;

    @ModelAttribute
    public FileDTO getFileDTO(){
        return new FileDTO();
    }
    @ModelAttribute
    public Note getNote(){
        return new Note();
    }
    @ModelAttribute
    public Credential getCredential(){
        return new Credential();
    }

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping("home/submit-note")
    public String addNote(@ModelAttribute("note") Note note, Model model) {
        if(note.getNoteId() > 0){
            noteService.edit(note);
        }
        else{
            int noteId = noteService.add(note);
        }
        return "redirect:/home";
    }

    @GetMapping("home/delete-note/{id}")
    public String deleteNote(@PathVariable("id") Integer id, Model model) {
        noteService.deleteById(id);
        return "redirect:/home";
    }
}
