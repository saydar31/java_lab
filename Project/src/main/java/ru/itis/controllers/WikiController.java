package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ru.itis.dto.WikiArticleLeafDto;
import ru.itis.dto.WikiFolderDto;
import ru.itis.model.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.WikiService;

@Controller
public class WikiController {
    @Autowired
    private WikiService wikiService;

    @GetMapping("/wiki")
    public ModelAndView getWikiPage() {
        ModelAndView modelAndView = new ModelAndView("wiki");
        modelAndView.addObject("folder", wikiService.getBaseFolder());
        return modelAndView;
    }

    @GetMapping("/wiki/folder/{folder-id:\\d+}")
    public ModelAndView getFolderPage(@PathVariable("folder-id") Long folderId) {
        ModelAndView modelAndView = new ModelAndView("wiki");
        modelAndView.addObject("folder", wikiService.getFolderById(folderId));
        return modelAndView;
    }

    @PostMapping("/wiki/folder/{folder-id:\\d+}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ModelAndView createFolder(@PathVariable("folder-id") Long folderId, @RequestParam("folderName") String folderName) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        WikiFolderDto wikiFolderDto = wikiService.createFolder(folderId, folderName);
        modelAndView.addObject("folderId", wikiFolderDto.getId());
        modelAndView.addObject("folderName", folderName);
        return modelAndView;
    }

    @GetMapping("/wiki/article/{article-id:\\d+}")
    public ModelAndView getArticlePage(@PathVariable("article-id") Long id) {
        ModelAndView modelAndView = new ModelAndView("article");
        modelAndView.addObject("article", wikiService.getArticleById(id));
        return modelAndView;
    }

    @GetMapping("/wiki/article/editor")
    @PreAuthorize(("isAuthenticated()"))
    public ModelAndView getEditor(@RequestParam("folderId") Long folderId) {
        ModelAndView modelAndView = new ModelAndView("articleEditor");
        modelAndView.addObject("folderId", folderId);
        return modelAndView;
    }

    @PostMapping("/wiki/{folder-id:\\d+}/article/editor")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addArticle(@PathVariable("folder-id") Long folderId, String article, String name, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        WikiArticleLeafDto articleDto = wikiService.createArticle(folderId, name, article, userDetails.getUser());
        return new ModelAndView("redirect:/wiki/article/" + articleDto.getId());
    }

    @PostMapping("/wiki/{folder-id:\\d+}/article/file")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addFileArticle(@PathVariable("folder-id") Long folderId, @RequestParam("name") String fileName, @RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        WikiArticleLeafDto leafDto = wikiService.createArticle(folderId, fileName, user, multipartFile);
        modelAndView.addObject("article", leafDto);
        return modelAndView;
    }

}