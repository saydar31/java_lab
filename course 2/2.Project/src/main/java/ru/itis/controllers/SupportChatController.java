package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.SupportChatService;

@Controller
public class SupportChatController {
    @Autowired
    private SupportChatService supportChatService;

    @GetMapping("/support")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getSupportChatPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("supportChat");
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        modelAndView.addObject("senderId", userDetails.getUser().getId());
        modelAndView.addObject("receiverId", 0);
        modelAndView.addObject("sendingUrl", "supportChat");
        modelAndView.addObject("dialog", supportChatService.getDialogByUser(userDetails.getUser()));
        return modelAndView;
    }

    @GetMapping("/support/{user-id:\\d+}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ModelAndView getAdminSupportChatPage(@PathVariable("user-id") Long userId, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("supportChat");
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        modelAndView.addObject("senderId", userDetails.getUser().getId());
        modelAndView.addObject("receiverId", userId);
        modelAndView.addObject("sendingUrl", "/supportChat/" + userId);
        modelAndView.addObject("dialog", supportChatService.getDialogByUserId(userId));
        return modelAndView;
    }

}
