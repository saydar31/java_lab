package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.service.UserService;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsersPage(){
        ModelAndView modelAndView = new ModelAndView("adminPage");
        modelAndView.addObject("users",userService.getUsers());
        return modelAndView;
    }
}
