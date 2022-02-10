package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Audio;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface AudiosService {

    List<Audio> findAll();
    void save(Audio audio);
    Audio getByTitle(String title);
    List<Audio> findFavorites(Long id);

    void sendAudio(HttpServletRequest request, HttpServletResponse response, File path) throws IOException;
    void handleFavorites(HttpServletRequest request, HttpServletResponse response);

}
