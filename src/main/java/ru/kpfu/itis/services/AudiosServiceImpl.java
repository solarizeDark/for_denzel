package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Audio;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.repositories.AudiosRepository;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.FavoritesRepository;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class AudiosServiceImpl implements AudiosService {

    private AudiosRepository audiosRepository;
    private FavoritesRepository favoritesRepository;
    private AuthRepository authRepository;

    public AudiosServiceImpl(AudiosRepository audiosRepository, FavoritesRepository favoritesRepository,
                             AuthRepository authRepository) {
        this.audiosRepository = audiosRepository;
        this.favoritesRepository = favoritesRepository;
        this.authRepository = authRepository;
    }

    @Override
    public List<Audio> findAll() {
        return audiosRepository.findAll();
    }

    @Override
    public void save(Audio audio) {
        audiosRepository.save(audio);
    }

    @Override
    public Audio getByTitle(String title) {
        return audiosRepository.getByTitle(title);
    }

    @Override
    public List<Audio> findFavorites(Long id) {
        return audiosRepository.findFavorites(id);
    }

    @Override
    public void sendAudio(HttpServletRequest request, HttpServletResponse response, File path) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        String title = request.getParameter("title");
        File audio = new File(path + File.separator + title);

        response.setContentType("audio/mpeg");
        response.setContentLength((int) audio.length());
        FileInputStream fileInputStream = new FileInputStream(audio);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

        int rb;
        while ((rb = bufferedInputStream.read()) != -1)
            outputStream.write(rb);
    }

    @Override
    public void handleFavorites(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        Cookie[] cookies = request.getCookies();
        String cookieValue = null;
        for (Cookie item : cookies) {
            if (item.getName().equals("auth")) {
                cookieValue = item.getValue();
            }
        }

        if (cookieValue == null) {
            throw new IllegalArgumentException();
        }

        Auth auth = authRepository.findByCookieValue(cookieValue);
        Audio audio = audiosRepository.getByTitle(title);

        String action = request.getHeader("Action");
        if (action.equals("add")) {
            favoritesRepository.save(audio.getId(), auth.getUser().getId());
        } else {
            favoritesRepository.delete(audio.getId(), auth.getUser().getId());
        }

    }

}
