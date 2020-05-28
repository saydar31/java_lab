package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.ForumDiscussionRecord;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class ForumDiscussionRepositoryImpl implements ForumDiscussionRecordRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(ForumDiscussionRecord entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<ForumDiscussionRecord> find(Long key) {
        return Optional.of(entityManager.find(ForumDiscussionRecord.class, key));
    }

    @Override
    @Transactional
    public List<ForumDiscussionRecord> getAll() {
        return entityManager.createQuery("select fdr from ForumDiscussionRecord fdr", ForumDiscussionRecord.class).getResultList();
    }

    @Override
    @Transactional
    public void update(ForumDiscussionRecord entity) {
        ForumDiscussionRecord fdr = entityManager.merge(entity);
        entity.setMessage(fdr.getMessage());
    }

    @Override
    @Transactional
    public void delete(ForumDiscussionRecord entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        entityManager.createQuery("delete from ForumDiscussionRecord fdr where fdr.id=:id").setParameter("id",id).executeUpdate();
    }
}
