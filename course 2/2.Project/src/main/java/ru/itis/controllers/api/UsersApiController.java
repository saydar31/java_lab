package ru.itis.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.dto.UserDto;
import ru.itis.service.UserService;

import java.util.List;

@RestController
public class UsersApiController {
    private final UserService userService;

    public UsersApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/users")
    public ResponseEntity<List<UserDto>> getUserDtoList() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping(value = "api/users/{page}/{size}")
    public ResponseEntity<List<UserDto>> getUserDtoList(@PathVariable("page") int page, @PathVariable("size") int size) {
        return ResponseEntity.ok(userService.getUsers(page, size));
    }

}
