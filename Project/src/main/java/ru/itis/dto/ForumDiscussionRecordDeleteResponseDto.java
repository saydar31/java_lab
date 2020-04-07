package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ForumDiscussionRecordDeleteResponseDto {
   private ForumDiscussionRecordDto discussionRecordDto;
   private boolean success;
}
