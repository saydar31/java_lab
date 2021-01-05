package ru.itis.documents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.documents.dto.SubscriptionDto;
import ru.itis.documents.services.SubscriptionService;

@RestController
public class SubscriptionController {
    @Autowired
    SubscriptionService subscriptionService;

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody SubscriptionDto subscriptionDto) {
        subscriptionService.subscribe(subscriptionDto);
    }
}
