package ru.itis.hateoas.services;

import ru.itis.hateoas.models.Course;

public interface VoteService {
    Course upvote(Long id);
    Course downvote(Long id);
}
