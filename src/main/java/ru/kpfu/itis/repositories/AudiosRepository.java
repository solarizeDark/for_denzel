package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Audio;

import java.util.List;

public interface AudiosRepository {

    List<Audio> findAll();
    void save(Audio audio);
    Audio getByTitle(String title);
    List<Audio> findFavorites(Long id);

}
