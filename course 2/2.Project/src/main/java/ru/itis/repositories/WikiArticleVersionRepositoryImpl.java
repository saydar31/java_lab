package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.WikiArticleVersion;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Component
public class WikiArticleVersionRepositoryImpl implements WikiArticleVersionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(WikiArticleVersion entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<WikiArticleVersion> find(Long key) {
        return Optional.of(entityManager.find(WikiArticleVersion.class, key));
    }

    @Override
    @Transactional
    public List<WikiArticleVersion> getAll() {
        return entityManager.createQuery("select wav from WikiArticleVersion wav", WikiArticleVersion.class).getResultList();
    }

    @Override
    @Transactional
    public void update(WikiArticleVersion entity) {
        WikiArticleVersion updatedVersion = entityManager.merge(entity);
        entity.setCreationDate(updatedVersion.getCreationDate());
        entity.setFileName(updatedVersion.getFileName());
        entity.setVersion(updatedVersion.getVersion());
    }

    @Override
    @Transactional
    public void delete(WikiArticleVersion entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
