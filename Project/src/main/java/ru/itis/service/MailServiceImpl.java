package ru.itis.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import ru.itis.model.Mail;
import ru.itis.model.User;
import ru.itis.service.util.EmailSender;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MailServiceImpl implements MailService {

    private final EmailSender emailSender;
    private final Configuration configuration;

    public MailServiceImpl(EmailSender emailSender, Configuration configuration) {
        this.emailSender = emailSender;
        this.configuration = configuration;
    }

    @Override
    public void sendSignUpLetter(User user, String verificationString) {
        try {
            Template template = configuration.getTemplate("mails/signUpLetter.ftl");
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> model = new HashMap<>();
            model.put("email", user.getEmail());
            model.put("hash", verificationString);
            template.process(model, stringWriter);
            String message = stringWriter.toString();
            Mail mail = new Mail(user.getEmail(), "sign up", message, List.of());
            emailSender.send(mail);
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void sendUploadNotification(User user, String filename) {
        try {
            Template template = configuration.getTemplate("mails/fileDownloadLetter.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("email", user.getEmail());
            model.put("fileName", filename);
            StringWriter stringWriter = new StringWriter();
            template.process(model, stringWriter);
            Mail mail = new Mail(user.getEmail(), "download", stringWriter.toString(), List.of());
            emailSender.send(mail);
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
