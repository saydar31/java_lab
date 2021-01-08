package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.InfoDto;
import ru.itis.service.InfoService;

@RestController
public class InfoRestController {
    @Autowired
    private InfoService infoService;
    @GetMapping("/api/info")
    public ResponseEntity<InfoDto> getInfo() {
        return ResponseEntity.ok(infoService.getInfo());
    }
}
