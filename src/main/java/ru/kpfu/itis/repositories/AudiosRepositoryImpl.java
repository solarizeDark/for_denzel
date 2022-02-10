package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.models.Audio;

import javax.sql.DataSource;
import java.util.List;

public class AudiosRepositoryImpl implements AudiosRepository {

    private JdbcTemplate template;

    //language=SQL
    private String SQL_FIND_ALL = "select * from audios";

    //language=SQL
    private String SQL_INSERT = "insert into audios(name) values (?)";

    //language=SQL
    private String SQL_FIND_BY_TITLE = "select * from audios where name = ?";

    //language=SQL
    private String SQL_FIND_FAVORITES =
            "select id, name from audios join favorites f on audios.id = f.audio_id\n" +
                    "where f.user_id = ?";


    public AudiosRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    private RowMapper<Audio> mapper =
            row -> Audio.builder()
                    .id(row.getLong("id"))
                    .title(row.getString("name"))
                    .build();

    @Override
    public List<Audio> findAll() {
        return template.query(SQL_FIND_ALL, mapper);
    }

    @Override
    public void save(Audio audio) {
        template.update(SQL_INSERT, audio.getTitle());
    }

    @Override
    public Audio getByTitle(String title) {
        return template.query(SQL_FIND_BY_TITLE, mapper, title).get(0);
    }

    @Override
    public List<Audio> findFavorites(Long id) {
        return template.query(SQL_FIND_FAVORITES, mapper, id);
    }

}
