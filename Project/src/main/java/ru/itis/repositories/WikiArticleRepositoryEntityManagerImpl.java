package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.WikiArticle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class WikiArticleRepositoryEntityManagerImpl implements WikiArticleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(WikiArticle entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<WikiArticle> find(Long key) {
        return Optional.of(entityManager.find(WikiArticle.class, key));
    }

    @Override
    @Transactional
    public List<WikiArticle> getAll() {
        return entityManager.createQuery("select wa from WikiArticle wa", WikiArticle.class).getResultList();
    }

    @Override
    @Transactional
    public void update(WikiArticle entity) {

    }

    @Override
    @Transactional
    public void delete(WikiArticle entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
