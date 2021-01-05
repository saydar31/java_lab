package ru.itis.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.documents.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSubscription {
    private User user;
    private SubscriptionDto subscriptionDto;
}
