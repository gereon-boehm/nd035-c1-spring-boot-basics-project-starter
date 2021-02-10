package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
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

    @GetMapping
    public String homeView(Authentication authentication, Model model) {
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
    public String uploadFile(@ModelAttribute("file") MultipartFile file, Model model) throws IOException {
        int fileId = fileService.uploadFile(file);
        model.addAttribute("fileList", fileService.getAllFiles());
        return "home";
    }

    @PostMapping("/view-file")
    public void viewFile() {
    }

    @DeleteMapping("/delete-file/{id}")
    public void deleteFile(@PathVariable("id") Integer id) {
        fileService.deleteById(id);
    }

    @PostMapping("/add-note")
    public String addCNote(@ModelAttribute("note") Note note, Model model) {
        noteService.add(note);
        model.addAttribute("noteList", noteService.getAllNotes());
        return "home";
    }

    @DeleteMapping("/delete-note/{id}")
    public void deleteNote(@PathVariable("id") Integer id) {
        noteService.deleteById(id);
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

    @DeleteMapping("/delete-credential/{id}")
    public void deleteCredentials(@PathVariable("id") Integer id) {
        credentialService.deleteById(id);
    }
}
