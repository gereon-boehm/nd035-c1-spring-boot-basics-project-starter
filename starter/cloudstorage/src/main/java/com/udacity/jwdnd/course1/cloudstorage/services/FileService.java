package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {
    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public int uploadFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        return fileMapper.insert(new File(null, fileName, file.getContentType(), file.getSize(), userService.getUserId(), file.getBytes()));
    }

    public List<File> getAllFiles() {
        List<File> fileList = fileMapper.getFiles(userService.getUserId());
        if(fileList != null)
            return fileList;
        else
            return new ArrayList<File>();
    }

    public void deleteById(Integer id) {
        fileMapper.delete(id);
    }
}
