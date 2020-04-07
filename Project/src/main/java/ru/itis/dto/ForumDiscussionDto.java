package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.ForumDiscussion;
import ru.itis.model.ForumDiscussionRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ForumDiscussionDto {
    private Long id;
    private String name;
    private Set<String> tags;
    private UserDto owner;
    private LocalDateTime lastChangeDate;
    private List<ForumDiscussionRecordDto> records;

    public static ForumDiscussionDto from(ForumDiscussion forumDiscussion) {
        ForumDiscussionDto forumDiscussionDto = ForumDiscussionDto.builder()
                .id(forumDiscussion.getId())
                .name(forumDiscussion.getName())
                .tags(forumDiscussion.getTags())
                .owner(UserDto.from(forumDiscussion.getOwner()))
                .lastChangeDate(forumDiscussion.getLastChange())
                .build();
        List<ForumDiscussionRecord> discussionRecords = forumDiscussion.getRecords();
        if (discussionRecords != null) {
            List<ForumDiscussionRecordDto> recordDtoList = discussionRecords.stream().map(ForumDiscussionRecordDto::from).collect(Collectors.toList());
            forumDiscussionDto.setRecords(recordDtoList);
        }
        return forumDiscussionDto;
    }
}
