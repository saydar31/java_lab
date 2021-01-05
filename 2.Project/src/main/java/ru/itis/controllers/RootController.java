package ru.itis.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UploadFileDto;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.UploadFileService;

import java.util.List;

@Controller
@Slf4j
public class RootController {
    @Autowired
    private UploadFileService uploadFileService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHomepage(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<UploadFileDto> files = uploadFileService.getUsersFiles(userDetails.getUser());
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("files", files);
        return modelAndView;
    }
}
