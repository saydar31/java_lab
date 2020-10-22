package ru.itis.documents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.SubscriptionDto;
import ru.itis.documents.dto.SubscriptionType;
import ru.itis.documents.dto.UserSubscription;
import ru.itis.documents.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SimpleSubscriptionCheckServiceImpl implements SimpleSubscriptionCheckService {
    @Autowired
    private PdfService pdfService;

    @Override
    public void simpleSubscribe(UserSubscription userSubscription) {
        Map<String, Object> model = new HashMap<>();
        if (userSubscription.getUser() == null) {
            userSubscription.setUser(User.builder()
                    .firstName("aydar")
                    .lastName("shaydullin")
                    .id(1L)
                    .mail("mail")
                    .build());
        }
        if (userSubscription.getSubscriptionDto() == null) {
            userSubscription.setSubscriptionDto(SubscriptionDto.builder()
                    .monthCount(2)
                    .subscriptionType(SubscriptionType.PREMIUM).build());
        }
        model.put("user", userSubscription.getUser());
        model.put("subscription", userSubscription.getSubscriptionDto());
        pdfService.createPdf(model, "simple", "ss-" + UUID.randomUUID().toString() + ".pdf");
    }
}
