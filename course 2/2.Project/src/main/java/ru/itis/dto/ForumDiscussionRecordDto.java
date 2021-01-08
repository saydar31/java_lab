package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.ForumDiscussionRecord;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ForumDiscussionRecordDto {
    private Long id;
    private UserDto user;
    private String message;
    private LocalDateTime date;
    public static ForumDiscussionRecordDto from(ForumDiscussionRecord forumDiscussionRecord){
        return ForumDiscussionRecordDto.builder()
                .id(forumDiscussionRecord.getId())
                .date(forumDiscussionRecord.getDate())
                .message(forumDiscussionRecord.getMessage())
                .user(UserDto.from(forumDiscussionRecord.getUser()))
                .build();
    }
}
