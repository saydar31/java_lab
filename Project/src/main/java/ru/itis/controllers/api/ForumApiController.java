package ru.itis.controllers.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.itis.dto.*;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.service.ForumService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class ForumApiController {
    private final ForumService forumService;

    public ForumApiController(ForumService forumService) {
        this.forumService = forumService;
    }

    @GetMapping("/api/forum")
    public ResponseEntity<List<ForumDiscussionDto>> getForumDiscussions(PageSizeDto pageSizeDto) {
        return ResponseEntity.ok(forumService.getForumDiscussions(pageSizeDto));
    }

    @DeleteMapping("/api/forum/forum_record/{record-id:\\d+}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<SuccessOperationDto> deleteRecord(@PathVariable("record-id") Long recordId) {
        forumService.delete(recordId);
        SuccessOperationDto successOperationDto = new SuccessOperationDto("delete forum record", true);
        return ResponseEntity.ok(successOperationDto);
    }

    @PostMapping("/forum/{forum-id:\\d+}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addDiscussionRecord(@Valid @RequestBody RecordDto recordDto, Authentication authentication, @PathVariable("forum-id") Long id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ForumDiscussionRecordDto recordDto1 = forumService.add(recordDto, userDetails.getUser(), id);
        return ResponseEntity.ok(recordDto1);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationError(MethodArgumentNotValidException exception) {
        Map<String, String> map = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(
                objectError -> {
                    FieldError fieldError = (FieldError) objectError;
                    String fieldName = fieldError.getField();
                    String message = objectError.getDefaultMessage();
                    map.put(fieldName, message);
                }
        );
        return map;
    }
}
