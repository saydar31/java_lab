package ru.itis.service.util;

import ru.itis.model.Mail;

public interface EmailSender {
    void send(Mail mail);
}
