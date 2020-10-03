package ru.itis.documents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.documents.dto.UserSubscription;

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
        model.put("user", userSubscription.getUser());
        model.put("subscription", userSubscription.getSubscriptionDto());
        pdfService.createPdf(model, "simple", UUID.randomUUID().toString() + ".pdf");
    }
}
