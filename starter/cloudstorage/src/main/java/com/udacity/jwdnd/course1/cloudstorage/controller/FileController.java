package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {
    private final FileService fileService;
    private int maxFileSize = 10000000;

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

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("home/file-upload")
    public String uploadFile(@ModelAttribute("fileDTO") MultipartFile file, Model model) throws IOException {
        if (file.getOriginalFilename().isEmpty()){
            model.addAttribute("error", "No file is selected");
        }
        else if(!fileService.isFileNameAvailable(file))
        {
            model.addAttribute("error", "File name already exists - Choose another file name.");
        }
        else if(!fileService.hasAllowedFileSize(file, maxFileSize)){
            model.addAttribute("error", "File is to big. Please only upload files with a maximum of "+ (int)(maxFileSize / 1000000.0) + "MB.");
        }
        else{
            try {
                int fileId = fileService.uploadFile(file);
                if(fileId > 0){
                    model.addAttribute("success", "File upload was successful.");
                }
                else{
                    model.addAttribute("error", "Upload failed. Please try again.");
                }
            } catch (Exception e) {
                model.addAttribute("error", "Upload failed. System error!" + e.getMessage());
            }
        }
        return "result";
    }

    @GetMapping("home/file-view/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable("id") Integer fileId)  {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFileName() + "\"").body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("home/delete-file/{id}")
    public String deleteFile(@PathVariable("id") Integer id, Model model) {
        try {
            fileService.deleteById(id);
            model.addAttribute("success", "File was successfully deleted.");
        } catch (Exception e) {
            model.addAttribute("error", "File could not be deleted. Please try again.");
        }
        return "result";
    }
}
