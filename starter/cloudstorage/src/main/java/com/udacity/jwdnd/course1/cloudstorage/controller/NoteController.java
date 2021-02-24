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
    private int maxTitleLength = 20;
    private int maxDescriptionLength = 1000;

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
        if(note.getNoteTitle().length() > maxTitleLength){
            model.addAttribute("error", "Note can't be saved as the title exceeds "+ maxTitleLength + " characters.");
        }
        else if(note.getNoteDescription().length() > maxDescriptionLength){
            model.addAttribute("error", "Note can't be saved as the description exceeds "+ maxDescriptionLength + " characters.");
        }
        else if(note.getNoteId() > 0){
            try {
                noteService.edit(note);
                model.addAttribute("success", "Note was successfully edited.");
            }
            catch (Exception e){
                model.addAttribute("error", "Failed to edit note. " + e.getMessage());
            }
        }
        else{
            try {
                noteService.add(note);
                model.addAttribute("success", "Note was successfully added.");
            }
            catch (Exception e){
                model.addAttribute("error", "Failed to add note. " + e.getMessage());
            }
        }
        return "result";
    }

    @GetMapping("home/delete-note/{id}")
    public String deleteNote(@PathVariable("id") Integer id, Model model) {
        try {
            noteService.deleteById(id);
            model.addAttribute("success", "Note was successfully deleted.");
        }
        catch (Exception e){
            model.addAttribute("error", "Failed to delete note. " + e.getMessage());
        }

        return "result";
    }
}
