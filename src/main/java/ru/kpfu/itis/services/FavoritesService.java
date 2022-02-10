package ru.kpfu.itis.services;

public interface FavoritesService {

    void save(Long audio_id, Long user_id);
    void delete(Long audio_id, Long user_id);

}
