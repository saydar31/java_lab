package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ru.itis.dto.UploadingDto;
import ru.itis.model.User;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.UploadFileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class FileController {

    @Autowired
    UploadFileService uploadFileService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.POST, produces = "application/json")
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        UploadingDto uploadingDto = uploadFileService.doService(multipartFile, user);
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("uploadingDto", uploadingDto);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public void getFile(Authentication authentication, HttpServletResponse response, @PathVariable("file-name") String fileName) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        Path path = uploadFileService.getRequestedFile(fileName, user);
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType(Files.probeContentType(path));
            response.addHeader("Content-Disposition", "inline; filename=" + fileName);
            Files.copy(path, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public ModelAndView getPage() {
        return new ModelAndView("upload_file");
    }
}
