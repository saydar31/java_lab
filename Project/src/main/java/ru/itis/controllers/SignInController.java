package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.service.SignInService;

@Controller
public class SignInController {

    @Autowired
    private SignInService signInService;

    @RequestMapping(value = "/sign_in", method = RequestMethod.GET)
    public ModelAndView getSignInPage() {
        return new ModelAndView("signIn");
    }

//    @RequestMapping(value = "/sign_in", method = RequestMethod.POST)
//    public ModelAndView signIn(SignInDto signInDto, HttpSession session) {
//        try {
//            session.setAttribute("userDto", signInService.verificationResult(signInDto));
//            return new ModelAndView("redirect:/files");
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//            throw new IllegalArgumentException(e);
//        }
//    }

}
