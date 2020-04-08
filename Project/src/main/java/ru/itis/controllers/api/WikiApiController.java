package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.WikiFolderDto;
import ru.itis.service.WikiService;

@RestController
public class WikiApiController {
    @Autowired
    private WikiService wikiService;

    @GetMapping("/api/wiki")
    public ResponseEntity<WikiFolderDto> getBaseFolder() {
        return ResponseEntity.ok(wikiService.getBaseFolder());
    }

    @GetMapping("/api/wiki/folder/{folder-id:\\d+}")
    public ResponseEntity<WikiFolderDto> getFolderById(@PathVariable("folder-id") Long folderId) {
        return ResponseEntity.ok(wikiService.getFolderById(folderId));
    }
}
