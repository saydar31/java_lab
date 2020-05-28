package ru.itis.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.UserDto;
import ru.itis.service.UserService;

@RestController
public class UserApiController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/user/{user-id:\\d+}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getUserById(@PathVariable("user-id") Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
