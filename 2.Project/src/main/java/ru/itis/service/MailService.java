package ru.itis.service;

import ru.itis.model.User;

public interface MailService {
    void sendSignUpLetter(User user,String verificationString);
    void sendUploadNotification(User user,String filename);
}
