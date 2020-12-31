package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public Integer createCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        return credentialMapper.insertCredential( new Credential(
                null,
                credential.getUrl(),
                credential.getUsername(),
                encryptedPassword,
                encodedSalt,
                credential.getUserId()
        ));
    }

    public Integer updateCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);

        return credentialMapper.updateCredential(new Credential(
                credential.getCredentialId(),
                credential.getUrl(),
                credential.getUsername(),
                encryptedPassword,
                encodedSalt,
                credential.getUserId()
        ));
    }

    public Integer deleteCredential(Integer credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }

    public List<Credential> getCredentials(Integer userId){
        return credentialMapper.getAllCredential(userId);
    }
}
