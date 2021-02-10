package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final UserService userService;

    public CredentialService(CredentialMapper credentialMapper, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.userService = userService;
    }

    public void addCredential(Credential credential) {
        String key = credential.getKey();
        credentialMapper.insert(new Credential(null, credential.getUrl(), credential.getUsername(), key, credential.getPassword(), userService.getUserId()));
    }

    public void deleteById(Integer id) {
        credentialMapper.delete(id);
    }

    public List<Credential> getAllCredentials() {
        List<Credential> credentialList = credentialMapper.getAllCredentials(userService.getUserId());
        if(credentialList != null)
            return credentialList;
        else
            return new ArrayList<Credential>();
    }
}
