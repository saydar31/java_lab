package ru.itis.service;

import org.springframework.stereotype.Component;
import ru.itis.dto.*;
import ru.itis.model.ForumDiscussion;
import ru.itis.model.ForumDiscussionRecord;
import ru.itis.model.User;
import ru.itis.repositories.ForumDiscussionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ForumServiceImpl implements ForumService {
    private final ForumDiscussionRepository forumDiscussionRepository;

    public ForumServiceImpl(ForumDiscussionRepository forumDiscussionRepository) {
        this.forumDiscussionRepository = forumDiscussionRepository;
    }

    @Override
    public List<ForumDiscussionDto> getForumDiscussions(PageSizeDto pageSizeDto) {

        if (pageSizeDto.getS() == null) {
            pageSizeDto.setS(20);
        }
        if (pageSizeDto.getP() == null) {
            pageSizeDto.setP(0);
        }
        return forumDiscussionRepository.getForumDiscussions(pageSizeDto.getP(), pageSizeDto.getS()).stream().map(ForumDiscussionDto::from).collect(Collectors.toList());
    }

    @Override
    public ForumDiscussionDto getForumDiscussion(String name) {
        Optional<ForumDiscussion> forumDiscussionOptional = forumDiscussionRepository.findByName(name);
        if (forumDiscussionOptional.isPresent()) {
            return ForumDiscussionDto.from(forumDiscussionOptional.get());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public ForumDiscussionDto getForumDiscussion(Long id) {
        Optional<ForumDiscussion> forumDiscussionOptional = forumDiscussionRepository.find(id);
        if (forumDiscussionOptional.isPresent()) {
            return ForumDiscussionDto.from(forumDiscussionOptional.get());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public ForumDiscussionDto getForumDiscussionPaginatedRecords(Long id, PageSizeDto pageSizeDto) {
        if (pageSizeDto.getS() == null) {
            pageSizeDto.setS(20);
        }
        if (pageSizeDto.getP() == null) {
            pageSizeDto.setP(0);
        }
        int page = pageSizeDto.getP();
        int size = pageSizeDto.getS();
        ForumDiscussionDto result = getForumDiscussion(id);
        List<ForumDiscussionRecordDto> recordList = result.getRecords();
        int recordListLength = recordList.size();
        int fromIndex = page * size < recordListLength - 1 ? page * size : 0;
        int toIndex = Math.min((page + 1) * size, recordListLength);
        toIndex = Math.max(toIndex, 0);
        result.setRecords(recordList.subList(fromIndex, toIndex));
        return result;
    }

    @Override
    public ForumDiscussionDto createDiscussion(User user, DiscussionDto discussionDto) {
        Set<String> tagSet = Set.of(discussionDto.getTags().split(";"));
        ForumDiscussion forumDiscussion = ForumDiscussion.builder()
                .name(discussionDto.getName())
                .tags(tagSet)
                .owner(user)
                .lastChange(LocalDateTime.now())
                .build();
        forumDiscussionRepository.save(forumDiscussion);
        return ForumDiscussionDto.from(forumDiscussion);
    }

    @Override
    public ForumDiscussionRecordDto add(RecordDto recordDto, User user, Long discussionId) {
        Optional<ForumDiscussion> forumDiscussionOptional = forumDiscussionRepository.find(discussionId);
        if (forumDiscussionOptional.isPresent()) {
            ForumDiscussion forumDiscussion = forumDiscussionOptional.get();
            ForumDiscussionRecord forumDiscussionRecord = ForumDiscussionRecord.builder()
                    .forumDiscussion(forumDiscussion)
                    .message(recordDto.getMessage())
                    .date(LocalDateTime.now())
                    .user(user)
                    .build();
            forumDiscussion.getRecords().add(forumDiscussionRecord);
            forumDiscussion.setLastChange(forumDiscussionRecord.getDate());
            forumDiscussionRepository.update(forumDiscussion);
            return ForumDiscussionRecordDto.from(forumDiscussionRecord);
        }else {
            throw new IllegalArgumentException("no such discussion");
        }
    }

    @Override
    public void delete(Long recordId) {
        forumDiscussionRepository.deleteForumRecordById(recordId);
    }
}
