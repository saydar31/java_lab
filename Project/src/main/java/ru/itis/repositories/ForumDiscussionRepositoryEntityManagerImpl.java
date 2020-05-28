package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.ForumDiscussion;
import ru.itis.model.ForumDiscussionRecord;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class ForumDiscussionRepositoryEntityManagerImpl implements ForumDiscussionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(ForumDiscussion entity) {
        entity.setOwner(entityManager.merge(entity.getOwner()));
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<ForumDiscussion> find(Long key) {
        ForumDiscussion forumDiscussion = entityManager.find(ForumDiscussion.class, key);
        return Optional.of(forumDiscussion);
    }

    @Override
    @Transactional
    public List<ForumDiscussion> getAll() {
        return entityManager.createQuery("select forumDiscussion from ForumDiscussion forumDiscussion order by forumDiscussion.lastChange desc", ForumDiscussion.class).getResultList();
    }

    @Override
    @Transactional
    public void update(ForumDiscussion entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(ForumDiscussion entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional
    public Optional<ForumDiscussion> findByName(String name) {
        return Optional.of(entityManager.createQuery("select forumDiscussion from ForumDiscussion forumDiscussion where forumDiscussion.name=:name", ForumDiscussion.class).setParameter("name", name).getSingleResult());
    }

    @Override
    @Transactional
    public List<ForumDiscussion> getForumDiscussions(int page, int size) {
        return entityManager.createQuery("select forumDiscussion from ForumDiscussion forumDiscussion order by forumDiscussion.lastChange desc", ForumDiscussion.class).setFirstResult(size * page).setMaxResults(size).getResultList();
    }

    @Override
    @Transactional
    public List<ForumDiscussion> findByTags(Collection<String> tags) {
        return entityManager.createQuery("select forumDiscussion from ForumDiscussion forumDiscussion where forumDiscussion.tags in :tags", ForumDiscussion.class).setParameter("tags", tags).getResultList();
    }
}
