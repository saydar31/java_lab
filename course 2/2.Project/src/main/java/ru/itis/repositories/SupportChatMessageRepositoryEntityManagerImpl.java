package ru.itis.repositories;

import org.springframework.stereotype.Component;
import ru.itis.model.SupportChatMessage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Component
public class SupportChatMessageRepositoryEntityManagerImpl implements SupportChatMessagesRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(SupportChatMessage entity) {
        entityManager.persist(entity);
    }

    @Override
    @Transactional
    public Optional<SupportChatMessage> find(Long key) {
        return Optional.ofNullable(entityManager.find(SupportChatMessage.class, key));
    }

    @Override
    @Transactional
    public List<SupportChatMessage> getAll() {
        return entityManager.createQuery("select scm from SupportChatMessage scm", SupportChatMessage.class).getResultList();
    }

    @Override
    @Transactional
    public void update(SupportChatMessage entity) {
        SupportChatMessage merged = entityManager.merge(entity);
        entity.setDateTime(merged.getDateTime());
        entity.setRead(merged.isRead());
        entity.setText(merged.getText());
        entity.setSender(merged.getSender());
    }

    @Override
    @Transactional
    public void delete(SupportChatMessage entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    @Transactional
    public List<SupportChatMessage> getUnreadMessagesByClientId(Long clientId) {
        return entityManager
                .createQuery("select scm from SupportChatMessage scm where (scm.dialog.client.id=:id) and scm.isRead=false", SupportChatMessage.class)
                .setParameter("id", clientId)
                .getResultList();
    }

    @Override
    @Transactional
    public Long getUnreadMessagesCount(Long clientId) {
        BigInteger bigIntegerResult = (BigInteger) entityManager.createNativeQuery("select count(*) from supportchatmessage join supportchatdialog s on supportchatmessage.dialog_id = s.id where client_id=:id and isread=false")
                .setParameter("id", clientId)
                .getSingleResult();
        return bigIntegerResult.longValue();
    }

}
