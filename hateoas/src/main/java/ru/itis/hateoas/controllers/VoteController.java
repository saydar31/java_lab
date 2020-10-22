package ru.itis.hateoas.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.hateoas.services.VoteService;

@RepositoryRestController
@AllArgsConstructor
@Slf4j
public class VoteController {
    private final VoteService voteService;

    @PutMapping("/courses/{id:\\d+}/upvote")
    @ResponseBody
    public ResponseEntity<?> upvote(@PathVariable Long id) {
        log.info("upvote");
        return ResponseEntity.ok(EntityModel.of(voteService.upvote(id)));
    }

    @PutMapping("/courses/{id:\\d+}/downvote")
    public ResponseEntity<?> downvote(@PathVariable Long id) {
        return ResponseEntity.ok(EntityModel.of(voteService.downvote(id)));
    }
}
