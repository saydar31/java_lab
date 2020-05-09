package ru.itis.service;

import ru.itis.dto.*;
import ru.itis.model.User;

import java.util.List;

public interface ForumService {
    List<ForumDiscussionDto> getForumDiscussions(PageSizeDto pageSizeDto);

    ForumDiscussionDto getForumDiscussion(String name);

    ForumDiscussionDto getForumDiscussion(Long id);

    ForumDiscussionDto getForumDiscussionPaginatedRecords(Long id,PageSizeDto pageSizeDto);

    ForumDiscussionDto createDiscussion(User user, DiscussionDto discussionDto);

    ForumDiscussionRecordDto add(RecordDto recordDto, User user, Long discussionId);

    void delete(Long recordId);
}
