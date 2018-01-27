package com.StockAlarms.StockAlarmsApplication.services.security;

import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * Created by Turian Ovidiu.
 * This is the interface for EncryptionService implementation.
 */

public interface EncryptionService {

    String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
    void setStrongPasswordEncryptor(StrongPasswordEncryptor strongEncryptor);
}
