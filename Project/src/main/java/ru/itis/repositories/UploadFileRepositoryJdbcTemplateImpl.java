package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.itis.model.Role;
import ru.itis.model.UploadFile;
import ru.itis.model.User;

import java.util.List;
import java.util.Optional;

@Component
public class UploadFileRepositoryJdbcTemplateImpl implements UploadFileRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_SAVE = "insert into files (original_name, user_id, current_name) VALUES (?,?,?) returning id";

    //language=SQL
    private final String SQL_FIND = "with files as (select * from files where id = ?) select * from files join project_user on files.user_id=project_user.id";

    //language=SQL
    private final String SQL_GET_ALL = "select * from files join project_user on files.user_id = project_user.id";

    //language=SQL
    private final String SQL_UPDATE = "update files set (original_name,user_id,current_name) = (?,?,?) where id=?";

    //language=SQL
    private final String SQL_DELETE = "delete from files where id=?";

    //language=SQL
    private final String SQL_FIND_BY_HASH = "with files as(select * from files where current_name = ?), project_user as(select * from project_user) select * from files join project_user on project_user.id=files.user_id";

    //language=SQL
    private final String SQL_FIND_BY_USER = "select * from files join project_user on files.user_id = project_user.id where user_id=?";

    private RowMapper<UploadFile> uploadFileRowMapper = ((resultSet, i) -> {
        Long id = resultSet.getLong(1);
        String originalName = resultSet.getString("original_name");
        String currentPath = resultSet.getString("current_name");
        Long userId = resultSet.getLong(5);
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String passwordHash = resultSet.getString("password_hash");
        Boolean isProofed = resultSet.getBoolean("is_proofed");
        Role role = Role.valueOf(resultSet.getString("role"));
        User user = new User(userId, firstName, lastName, email, passwordHash, isProofed, role);
        return new UploadFile(id, originalName, currentPath, user);
    });

    @Override
    public void save(UploadFile entity) {
        Long id = jdbcTemplate.queryForObject(SQL_SAVE, ((resultSet, i) -> resultSet.getLong("id")), entity.getOriginalName(), entity.getUser().getId(), entity.getCurrentPath());
        entity.setId(id);
    }

    @Override
    public Optional<UploadFile> find(Long key) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_FIND, uploadFileRowMapper, key));
    }

    @Override
    public List<UploadFile> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, uploadFileRowMapper);
    }

    @Override
    public void update(UploadFile entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getOriginalName(), entity.getUser().getId(), entity.getCurrentPath(), entity.getId());
    }

    @Override
    public void delete(UploadFile entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getId());
    }

    @Override
    public Optional<UploadFile> findByHash(String hash) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_HASH, uploadFileRowMapper, hash));
    }

    @Override
    public List<UploadFile> findAllByUser(User user) {
        return jdbcTemplate.query(SQL_FIND_BY_USER, uploadFileRowMapper, user.getId());
    }
}
