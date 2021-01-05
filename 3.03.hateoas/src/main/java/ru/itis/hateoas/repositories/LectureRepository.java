package ru.itis.hateoas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.hateoas.models.Lecture;

public interface LectureRepository  extends JpaRepository<Lecture,Long> {
}
