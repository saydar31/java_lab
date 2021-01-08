package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.SuccessOperationDto;
import ru.itis.dto.SupportChatMessageDto;
import ru.itis.service.SupportChatService;

import java.util.List;

@RestController
public class SupportChatRestController {
    @Autowired
    private SupportChatService supportChatService;

    @PostMapping("/supportChat")
    public ResponseEntity<SuccessOperationDto> receiveMessage(@RequestParam("text") String text, @RequestParam("senderId") Long senderId, Authentication authentication) {
        supportChatService.addMessage(senderId, text, authentication);
        return ResponseEntity.ok(SuccessOperationDto.builder()
                .isSuccess(true)
                .operationName("add message")
                .build());
    }

    @GetMapping("/supportChat")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SupportChatMessageDto>> getMessages(Authentication authentication) {
        return ResponseEntity.ok(supportChatService.getUnreadMessages(authentication));
    }

    @PostMapping("/supportChat/{client-id:\\d+}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SuccessOperationDto> receiveMessage(@PathVariable("client-id") Long clientId, @RequestParam("text") String text, Authentication authentication) {
        supportChatService.addMessage(clientId, text, authentication);
        return ResponseEntity.ok(SuccessOperationDto.builder()
                .isSuccess(true)
                .operationName("add message")
                .build());
    }

    @GetMapping("/supportChat/{client-id:\\d+}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<SupportChatMessageDto>> getMessages(@PathVariable("client-id") Long clientId) {
        return ResponseEntity.ok(supportChatService.getUnreadMessages(clientId));
    }
}
