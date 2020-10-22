package ru.itis.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.models.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
