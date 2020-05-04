package ru.itis.javalabmessagequeue.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.javalabmessagequeue.dto.QueueNameDto;
import ru.itis.javalabmessagequeue.dto.SuccessOperationDto;
import ru.itis.javalabmessagequeue.service.QueueService;

@RestController
public class QueueController {
    @Autowired
    private QueueService queueService;

    @PostMapping("/queue")
    public ResponseEntity<SuccessOperationDto> addQueue(@RequestBody QueueNameDto queueNameDto) {
        return ResponseEntity.ok(queueService.createQueue(queueNameDto));
    }
}
