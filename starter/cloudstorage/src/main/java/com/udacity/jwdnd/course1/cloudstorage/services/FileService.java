package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public int uploadFile(MultipartFile file) throws IOException {
        return fileMapper.insert(new File(null, file.getOriginalFilename(), file.getContentType(), file.getSize(), userService.getUserId(), file.getBytes()));
    }

    public List<File> getAllFiles() {
        List<File> fileList = fileMapper.getFiles(userService.getUserId());
        if(fileList != null)
            return fileList;
        else
            return new ArrayList<File>();
    }

    public File getFile(Integer fileId){
        return fileMapper.getFile(fileId);
    }

    public void deleteById(Integer id) {
        fileMapper.deleteById(id);
    }

    public boolean isFileNameAvailable(MultipartFile multipartFile)
    {
        boolean fileNameIsAvailable = true;
        List<File> fileList = fileMapper.getFiles(userService.getUserId());
        String fileName = multipartFile.getOriginalFilename();
        if(fileList != null){
            for(File file : fileList)
            {
                System.out.println(file.getFileName());
                if(fileName.equals(file.getFileName()))
                {
                    fileNameIsAvailable = false;
                    break;
                }
            }
        }
        return fileNameIsAvailable;
    }

    public boolean hasAllowedFileSize(MultipartFile file, int maxFileSize){
        System.out.println("file.getSize(): " + file.getSize());
        if(file.getSize() < maxFileSize){
            return true;
        }
        else{
            return false;
        }
    }
}
