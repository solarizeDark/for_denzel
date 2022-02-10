package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.models.User;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {

    private JdbcTemplate template;

    //language=sql
    private final String SQL_INSERT_USER = "INSERT INTO users(first_name, last_name, login, password_hash) VALUES (?, ?, ?, ?)";
    //language=sql
    private final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?;";


    public UsersRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    public static RowMapper<User> rowMapper =
            row ->
                    User.builder()
                            .id(row.getLong("id"))
                            .login(row.getString("login"))
                            .passwordHash(row.getString("password_hash"))
                            .firstName(row.getString("first_name"))
                            .lastName(row.getString("last_name"))
                            .build();

    @Override
    public User save(User user) {
        template.update(SQL_INSERT_USER, user.getFirstName(), user.getLastName(),
                user.getLogin(), user.getPasswordHash());
        return user;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public User findByLogin(String login) {
        return template.query(SQL_FIND_USER_BY_LOGIN, rowMapper, login).get(0);
    }
}
