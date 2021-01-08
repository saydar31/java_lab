package ru.itis.service.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.model.Mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component
public class EmailSenderImpl implements EmailSender {

    @Autowired
    private Session session;

    public void send(Mail mail) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("ajdarshaydullin@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getDestination()));
            message.setSubject(mail.getSubject());
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mail.getMessage(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            List<File> attachments = mail.getAttachments();
            if (attachments.size() > 0) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                for (File file :
                        attachments) {
                    attachmentBodyPart.attachFile(file);
                }
            }
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

    }
}