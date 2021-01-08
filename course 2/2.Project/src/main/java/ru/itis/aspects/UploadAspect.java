package ru.itis.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UploadingDto;
import ru.itis.model.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.MailService;

import java.util.Map;

@Aspect
@Component
public class UploadAspect {

    @Autowired
    private MailService mailService;

    @AfterReturning(value = "execution(* ru.itis.controllers.FileController.uploadFile(..))", returning = "modelAndView")
    public void after(JoinPoint joinPoint, ModelAndView modelAndView) {
        Object[] args = joinPoint.getArgs();
        Authentication authentication = (Authentication) args[1];
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Map<String, Object> model = modelAndView.getModel();
        UploadingDto uploadingDto = (UploadingDto) model.get("uploadingDto");
        mailService.sendUploadNotification(userDetails.getUser(), uploadingDto.getFileHash());
    }
}
