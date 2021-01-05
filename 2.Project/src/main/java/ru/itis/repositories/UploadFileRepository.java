package ru.itis.repositories;

import ru.itis.model.UploadFile;
import ru.itis.model.User;

import java.util.List;
import java.util.Optional;

public interface UploadFileRepository extends CrudRepository<UploadFile,Long> {
    Optional<UploadFile> findByHash(String hash);
    List<UploadFile> findAllByUser(User user);
}
