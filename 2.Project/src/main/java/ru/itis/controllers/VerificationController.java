package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UserDto;
import ru.itis.model.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.ConfirmationService;

import java.security.Principal;

@Controller
public class VerificationController {
    @Autowired
    private ConfirmationService confirmationService;

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/proof", method = RequestMethod.GET)
    public ModelAndView proof(@RequestParam("hash") String hash, Authentication authentication) {
        confirmationService.verify(hash);
        return new ModelAndView("homePage");
    }

}
