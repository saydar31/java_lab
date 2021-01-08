package ru.itis.javalabmessagequeue.controllers.command;

import com.etolmach.spring.jcommander.annotation.CommandController;
import com.etolmach.spring.jcommander.annotation.CommandHandler;
import com.etolmach.spring.jcommander.annotation.CommandParameter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.javalabmessagequeue.service.AuthenticationService;

@CommandController
public class AuthenticationCommandController {
    @Autowired
    private AuthenticationService authenticationService;

    @CommandHandler(command = "authentication")
    public void setKey(@CommandParameter(name = "-key") String key) {
        authenticationService.setKey(key);
    }
}
