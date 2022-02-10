package ru.kpfu.itis.repositories;

import javax.sql.DataSource;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private JdbcTemplate template;

    //language=SQL
    private String SQL_INSERT = "insert into favorites(audio_id, user_id) values (?, ?)";

    //language=SQL
    private String SQL_DELETE = "delete from favorites where audio_id = ? and user_id = ?";

    public FavoritesRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Long audio_id, Long user_id) {
        template.update(SQL_INSERT, audio_id, user_id);
    }

    @Override
    public void delete(Long audio_id, Long user_id) {
        template.update(SQL_DELETE, audio_id, user_id);
    }
}
