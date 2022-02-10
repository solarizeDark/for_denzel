package ru.kpfu.itis.services;

import ru.kpfu.itis.repositories.FavoritesRepository;

public class FavoritesServiceImpl implements FavoritesService {

    private FavoritesRepository favoritesRepository;

    public FavoritesServiceImpl(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    @Override
    public void save(Long audio_id, Long user_id) {
        favoritesRepository.save(audio_id, user_id);
    }

    @Override
    public void delete(Long audio_id, Long user_id) {
        favoritesRepository.delete(audio_id, user_id);
    }
}
