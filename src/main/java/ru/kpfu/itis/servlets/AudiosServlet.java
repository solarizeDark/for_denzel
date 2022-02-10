package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Audio;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.services.AudiosService;
import ru.kpfu.itis.services.AuthService;
import ru.kpfu.itis.services.FavoritesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/audios")
@MultipartConfig
public class AudiosServlet extends HttpServlet {

    private File path;
    private AudiosService audiosService;
    private AuthService authService;
    private FavoritesService favoritesService;

    @Override
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        this.path = new File((String) context.getAttribute("diskPath"));
        this.audiosService = (AudiosService) context.getAttribute("audiosService");
        this.authService = (AuthService) context.getAttribute("authService");
        this.favoritesService = (FavoritesService) context.getAttribute("favoritesService");
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getHeader("Type") != null && request.getHeader("Type").equals("ajax")) {
            audiosService.sendAudio(request, response, path);
        } else {
            request.setAttribute("audios", audiosService.findAll());
            request.getRequestDispatcher("/WEB-INF/jsp/audios.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // part or form item that was received within a multipart/form-data POST request
        for (Part part : request.getParts()) {
            String type = part.getContentType();
            if (!type.equals("audio/mpeg")) {
                request.setAttribute("answer", "File is not audio");
                request.getRequestDispatcher("/WEB-INF/jsp/audios.jsp").forward(request, response);
                return;
            }
            String fileName = part.getSubmittedFileName();
            part.write(path + File.separator + fileName);
            audiosService.save(Audio.builder().title(fileName).build());
        }
        request.setAttribute("answer", "Uploaded");
        request.getRequestDispatcher("/WEB-INF/jsp/audios.jsp").forward(request, response);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        audiosService.handleFavorites(request, response);
    }

}
