package ru.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.service.SignUpService;

@Controller
public class SignUpController {

    private final SignUpService signUpService;

    public SignUpController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/sign_up", method = RequestMethod.GET)
    public ModelAndView getSignUpPage() {
        return new ModelAndView("signUp");
    }

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    public ModelAndView signUp(@RequestParam("email") String email, @RequestParam("password") String password) {
        signUpService.signUp(email, password);
        return new ModelAndView("redirect:/sign_in");
    }

}
