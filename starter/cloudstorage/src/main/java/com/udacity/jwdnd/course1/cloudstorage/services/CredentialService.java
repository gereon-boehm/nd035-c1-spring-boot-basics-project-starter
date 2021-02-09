package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.util.List;

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
        return credentialMapper.getAllCredentials();
    }
}
