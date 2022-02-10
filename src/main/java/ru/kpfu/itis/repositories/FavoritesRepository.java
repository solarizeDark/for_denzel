package ru.kpfu.itis.repositories;

public interface FavoritesRepository {

    void save(Long audio_id, Long user_id);
    void delete(Long audio_id, Long user_id);

}
