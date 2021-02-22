package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;
    private final UserService userService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, UserService userService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.userService = userService;
    }

    public void add(Credential credential) {
        String encodedKey = generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credentialMapper.insert(
                new Credential(null, credential.getUrl(), credential.getUsername(), encodedKey, encryptedPassword, userService.getUserId())
        );
    }

    public void edit(Credential credential){
        String encodedKey = generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        credentialMapper.update(credential);
    }

    public void deleteById(Integer id) {
        credentialMapper.delete(id);
    }

    public List<Credential> getAllCredentials() {
        List<Credential> credentialList = credentialMapper.getAllCredentials(userService.getUserId());
        if(credentialList != null) {
            return credentialList;
        }
        else
            return new ArrayList<Credential>();
    }

    private String generateKey()
    {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
