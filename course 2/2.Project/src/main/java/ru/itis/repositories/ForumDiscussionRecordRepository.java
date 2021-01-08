package ru.itis.repositories;

import ru.itis.model.ForumDiscussionRecord;

public interface ForumDiscussionRecordRepository extends CrudRepository<ForumDiscussionRecord,Long> {
    void deleteById(Long id);
}
