package com.dimasta.learn.toDoMicroservice.utilities;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {


    //attribute from jasypt library
    private final BasicTextEncryptor textEncryptor;


    /* constructor */
    public EncryptionUtils() {
        textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("mySecretEncriptionKeyBlaBla1234");
    }


    public String encrypt(String data) {
        return textEncryptor.encrypt(data);
    }

    public String decrypt(String encriptedData) {
        return textEncryptor.decrypt(encriptedData);
    }


}
