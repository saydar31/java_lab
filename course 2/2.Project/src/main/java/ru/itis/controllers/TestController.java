package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.service.TestService;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/test")
    @PreAuthorize("permitAll()")
    public ModelAndView getTestPage() {
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("test",testService.getTestByName("Choose your country"));
        return modelAndView;
    }

    @PostMapping("test")
    public ModelAndView test(){
        return new ModelAndView();
    }

}
