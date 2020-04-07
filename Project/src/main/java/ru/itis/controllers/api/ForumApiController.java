package ru.itis.controllers.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.ForumDiscussionDto;
import ru.itis.dto.SuccessOperationDto;
import ru.itis.service.ForumService;

import java.util.List;

@RestController
public class ForumApiController {
    private final ForumService forumService;

    public ForumApiController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/api/forum")
    public ResponseEntity<List<ForumDiscussionDto>> getForumDiscussions(@RequestParam(value = "p", required = false) Integer page, @RequestParam(value = "s", required = false) Integer size) {
        return ResponseEntity.ok(forumService.getForumDiscussions(page, size));
    }

    @DeleteMapping("/api/forum/forum_record/{record-id:\\d+}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SuccessOperationDto> deleteRecord(@PathVariable("record-id") Long recordId) {
        forumService.delete(recordId);
        SuccessOperationDto successOperationDto = new SuccessOperationDto("delete forum record", true);
        return ResponseEntity.ok(successOperationDto);
    }

}
