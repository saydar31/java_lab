package ru.itis.repositories;

import ru.itis.model.ForumDiscussion;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ForumDiscussionRepository extends CrudRepository<ForumDiscussion, Long> {
    Optional<ForumDiscussion> findByName(String name);

    List<ForumDiscussion> getForumDiscussions(int page, int size);

    void deleteForumRecordById(Long id);

    List<ForumDiscussion> findByTags(Collection<String> tags);
}
