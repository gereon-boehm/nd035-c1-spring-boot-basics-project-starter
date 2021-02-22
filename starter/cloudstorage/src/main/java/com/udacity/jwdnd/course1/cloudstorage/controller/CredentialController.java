package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CredentialController {
    private final CredentialService credentialService;

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

    public CredentialController(CredentialService credentialService){
        this.credentialService = credentialService;
    }

    @PostMapping("home/submit-credential")
    public String addCredential(@ModelAttribute("credential") Credential credential, Model model) {
        if(credential.getCredentialId() > 0){
            credentialService.edit(credential);
        }
        else{
            credentialService.add(credential);
        }
        return "redirect:/home";
    }

    @GetMapping("home/delete-credential/{id}")
    public String deleteCredentials(@PathVariable("id") Integer id, Model model) {
        credentialService.deleteById(id);
        return "redirect:/home";
    }
}
