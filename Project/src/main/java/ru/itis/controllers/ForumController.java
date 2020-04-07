package ru.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.DiscussionDto;
import ru.itis.dto.ForumDiscussionDto;
import ru.itis.dto.RecordDto;
import ru.itis.model.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.ForumService;

@Controller
public class ForumController {
    private final ForumService forumService;

    public ForumController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/forum")
    public ModelAndView getForumPage(@RequestParam(value = "p", required = false, defaultValue = "0") Integer page, @RequestParam(value = "s", required = false,defaultValue = "20") Integer size) {
        ModelAndView modelAndView = new ModelAndView("forums");
        modelAndView.addObject("page", page);
        modelAndView.addObject("size", size);
        modelAndView.addObject("discussions", forumService.getForumDiscussions(page, size));
        return modelAndView;
    }

    @PostMapping("/forum")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView newDiscussion(DiscussionDto discussionDto, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        ForumDiscussionDto forumDiscussion = forumService.createDiscussion(user, discussionDto);
        return new ModelAndView("redirect:/forum/" + forumDiscussion.getId());
    }

    @GetMapping("/forum/{forum-id:\\d+}")
    @PreAuthorize("permitAll()")
    public ModelAndView getForumDiscussionPage(@PathVariable("forum-id") Long id, @RequestParam(value = "p", required = false,defaultValue = "0") Integer page, @RequestParam(value = "s", required = false,defaultValue = "20") Integer size) {
        ModelAndView modelAndView = new ModelAndView("forumDiscussion");
        modelAndView.addObject("discussion", forumService.getForumDiscussionPaginatedRecords(id, page, size));
        modelAndView.addObject("page",page);
        modelAndView.addObject("size", size);
        return modelAndView;
    }

    @PostMapping("/forum/{forum-id:\\d+}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addRecord(RecordDto recordDto, Authentication authentication, @PathVariable("forum-id") Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        forumService.add(recordDto, user, id);
        return new ModelAndView("redirect:/forum/" + id);
    }

}
