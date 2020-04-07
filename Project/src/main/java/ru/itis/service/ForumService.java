package ru.itis.service;

import ru.itis.dto.DiscussionDto;
import ru.itis.dto.ForumDiscussionDto;
import ru.itis.dto.RecordDto;
import ru.itis.model.User;

import java.util.List;

public interface ForumService {
    List<ForumDiscussionDto> getForumDiscussions(Integer page, Integer size);

    ForumDiscussionDto getForumDiscussion(String name);

    ForumDiscussionDto getForumDiscussion(Long id);

    ForumDiscussionDto getForumDiscussionPaginatedRecords(Long id, Integer page, Integer size);

    ForumDiscussionDto createDiscussion(User user, DiscussionDto discussionDto);

    void add(RecordDto recordDto, User user, Long discussionId);

    void delete(Long recordId);
}
