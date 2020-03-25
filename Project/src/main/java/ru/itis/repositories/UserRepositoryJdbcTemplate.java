package ru.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.model.Role;
import ru.itis.model.User;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryJdbcTemplate implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=SQL
    private final String SQL_SAVE_USER = "insert into project_user (first_name, last_name, email,password_hash) values (?,?,?,?) returning id";

    //language=SQL
    private final String SQL_FIND_USER = "select * from project_user where id=? limit 1";

    //language=SQL
    private final String SQL_GET_ALL = "select * from project_user";

    //language=SQL
    private final String SQL_UPDATE = "update project_user set (first_name,last_name,email,password_hash,is_proofed,role) = (?,?,?,?,?,?) where id=?";

    //language=SQL
    private final String SQL_DELETE = "delete from project_user where id=?";

    //language=SQL
    private final String SQL_PAGINATION = "select * from project_user limit ? offset ?";

    //language=SQL
    private final String SQL_EMAIL_EXISTS = "select * from project_user where email=? limit 1";

    //language=SQL
    private final String SQL_MAKE_PROOFED = "update  project_user set is_proofed = true where id =?";

    RowMapper<User> userRowMapper = (resultSet, i) -> {
        Long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String email = resultSet.getString("email");
        String passwordHash = resultSet.getString("password_hash");
        boolean proofed = resultSet.getBoolean("is_proofed");
        Role role = Role.valueOf(resultSet.getString("role"));
        return new User(id, firstName, lastName, email, passwordHash, proofed, role);
    };

    public UserRepositoryJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User entity) {
        Long generatedId = jdbcTemplate.queryForObject(SQL_SAVE_USER, ((resultSet, i) -> resultSet.getLong("id")), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassWordHash());
        entity.setId(generatedId);
    }

    public Optional<User> find(Long key) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_USER, userRowMapper, key));
    }

    public List<User> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, userRowMapper);
    }

    public void update(User entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPassWordHash(), entity.isProofed(),entity.getRole().name(), entity.getId());
    }

    public void delete(User entity) {
        jdbcTemplate.update(SQL_DELETE, entity.getId());
    }

    public List<User> getUsers(int page, int size) {
        return jdbcTemplate.query(SQL_PAGINATION, userRowMapper, size, size * page);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_EMAIL_EXISTS, userRowMapper, email));

    }

    @Override
    public void makeProofed(User user) {
        jdbcTemplate.update(SQL_MAKE_PROOFED, user.getId());
    }
}
