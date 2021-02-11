package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(FileService fileService, NoteService noteService, CredentialService credentialService){
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @ModelAttribute("fileDTO")
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

    @GetMapping
    public String homeView(Model model) {
        model.addAttribute("fileList", fileService.getAllFiles());
        model.addAttribute("noteList", noteService.getAllNotes());
        model.addAttribute("credentialList", credentialService.getAllCredentials());
        return "home";
    }

    @PostMapping("/logout")
    public String logoutView(Model model) {
        model.addAttribute("logout", true);
        return "login";
    }

    @PostMapping("/file-upload")
    public String uploadFile(@ModelAttribute("fileDTO") MultipartFile file, Model model) throws IOException {
        int fileId = fileService.uploadFile(file);
        model.addAttribute("fileList", fileService.getAllFiles());
        return "home";
    }

    @PostMapping("/view-file")
    public void viewFile() {
    }

    @GetMapping("/delete-file/{id}")
    public String deleteFile(@PathVariable("id") Integer id, Model model) {
        fileService.deleteById(id);
        model.addAttribute("fileList", fileService.getAllFiles());
        return "home";
    }

    @PostMapping("/add-note")
    public String addNote(@ModelAttribute("note") Note note, Model model) {
        noteService.add(note);
        model.addAttribute("noteList", noteService.getAllNotes());
        return "home";
    }

    @GetMapping("/delete-note/{id}")
    public String deleteNote(@PathVariable Integer id, Model model) {
        noteService.deleteById(id);
        model.addAttribute("noteList", noteService.getAllNotes());
        return "home";
    }

    @PostMapping("/add-credential")
    public String addCredential(@ModelAttribute("credential") Credential credential, Model model) {
        credentialService.addCredential(credential);
        model.addAttribute("credentialList", credentialService.getAllCredentials());
        return "home";
    }

    @PostMapping("/edit-credential")
    public void editCredentials() {
    }

    @GetMapping("/delete-credential/{id}")
    public String deleteCredentials(@PathVariable Integer id, Model model) {
        credentialService.deleteById(id);
        model.addAttribute("credentialList", credentialService.getAllCredentials());
        return "home";
    }
}
