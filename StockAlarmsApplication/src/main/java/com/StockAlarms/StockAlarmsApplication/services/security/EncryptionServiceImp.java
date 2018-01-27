package com.StockAlarms.StockAlarmsApplication.services.security;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Turian Ovidiu.
 * This class represent the implementation of EncryptionService interface.
 */

@Service
public class EncryptionServiceImp implements EncryptionService {

    private StrongPasswordEncryptor strongEncryptor;

    @Autowired
    public void setStrongPasswordEncryptor(StrongPasswordEncryptor strongEncryptor) {
        this.strongEncryptor = strongEncryptor;
    }

    @Override
    public String encryptString(String input) {
        return  strongEncryptor.encryptPassword(input);
    }


    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return strongEncryptor.checkPassword(plainPassword, encryptedPassword);
    }
}
