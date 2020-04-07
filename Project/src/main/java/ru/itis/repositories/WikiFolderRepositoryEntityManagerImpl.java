package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.WikiFolder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class WikiFolderRepositoryEntityManagerImpl implements WikiFolderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(WikiFolder entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<WikiFolder> find(Long key) {
        return Optional.of(entityManager.find(WikiFolder.class, key));
    }

    @Override
    @Transactional
    public List<WikiFolder> getAll() {
        return entityManager.createQuery("select wf from WikiFolder as wf", WikiFolder.class).getResultList();
    }

    @Override
    @Transactional
    public void update(WikiFolder entity) {
        entityManager.merge(entity);
    }

    @Override
    @Transactional
    public void delete(WikiFolder entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
