package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class AuthRepostoryImpl implements AuthRepository {

    private JdbcTemplate template;

    //language=SQL
    private final String SQL_FIND_BY_COOKIE_VALUE =
            "SELECT *, auth.id as auth_id, users.id as user_id FROM auth INNER JOIN users ON auth.user_id=users.id " +
                    "WHERE auth.cookie_value=?";
    //language=SQL
    private final String SQL_INSERT_AUTH = "INSERT INTO auth (user_id, cookie_value) VALUES (?, ?)";


    public AuthRepostoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Auth findByCookieValue(String cookieValue) {
        return template.query(SQL_FIND_BY_COOKIE_VALUE, rowMapper, cookieValue).get(0);
    }

    @Override
    public List<Auth> findAll() {
        return null;
    }

    @Override
    public Optional<Auth> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Auth save(Auth auth) {
        template.update(SQL_INSERT_AUTH, auth.getUser().getId(), auth.getCookieValue());
        return auth;
    }

    @Override
    public void deleteById(Long id) {

    }

    RowMapper<Auth> rowMapper =
            row -> {
                Auth auth = Auth.builder()
                        .id(row.getLong("auth_id"))
                        .cookieValue(row.getString("cookie_value"))
                        .build();

                User user = User.builder()
                        .login(row.getString("login"))
                        .lastName(row.getString("first_name"))
                        .firstName("last_name")
                        .id(row.getLong("user_id"))
                        .passwordHash(row.getString("password_hash"))
                        .build();
                auth.setUser(user);
                return auth;
            };
}
