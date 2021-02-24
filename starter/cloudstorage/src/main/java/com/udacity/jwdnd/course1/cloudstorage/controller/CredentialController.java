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
            try {
                credentialService.edit(credential);
                model.addAttribute("success", "Credential was successfully edited.");
            }
            catch (Exception e){
                model.addAttribute("error", "Failed to edit credential. " + e.getMessage());
            }
        }
        else{
            try {
                credentialService.add(credential);
                model.addAttribute("success", "Credential was successfully added.");
            }
            catch (Exception e){
                model.addAttribute("error", "Failed to add credential. " + e.getMessage());
            }
        }
        return "result";
    }

    @GetMapping("home/delete-credential/{id}")
    public String deleteCredentials(@PathVariable("id") Integer id, Model model) {
        try {
            credentialService.deleteById(id);
            model.addAttribute("success", "Credential was successfully deleted.");
        }
        catch (Exception e){
            model.addAttribute("error", "Failed to delete credential. " + e.getMessage());
        }
        return "result";
    }
}
