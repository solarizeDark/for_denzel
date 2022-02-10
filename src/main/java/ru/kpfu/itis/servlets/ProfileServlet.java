package ru.kpfu.itis.servlets;

import ru.kpfu.itis.models.Audio;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.services.AudiosService;
import ru.kpfu.itis.services.AuthService;
import ru.kpfu.itis.services.FavoritesService;
import ru.kpfu.itis.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private File path;
    private UsersService usersService;
    private AudiosService audiosService;
    private AuthService authService;
    private FavoritesService favoritesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        this.usersService = (UsersService) context.getAttribute("usersService");
        this.audiosService = (AudiosService) context.getAttribute("audiosService");
        this.path = new File( (String) context.getAttribute("diskPath") );
        this.authService = (AuthService) context.getAttribute("authService");
        this.favoritesService = (FavoritesService) context.getAttribute("favoritesService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getHeader("Type") != null && request.getHeader("Type").equals("ajax")) {
            audiosService.sendAudio(request, response, path);
            return;
        }

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                User user = usersService.findUserByCookieValue(cookie.getValue());
                if (user != null) {

                    List<Audio> favorites = audiosService.findFavorites(user.getId());

                    request.setAttribute("user", user);
                    request.setAttribute("favorites", favorites);
                    request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
                }
            }
        }

        response.sendRedirect("/signIn");

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
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

        Auth auth = authService.getByCookieValue(cookieValue);
        Audio audio = audiosService.getByTitle(title);
        favoritesService.delete(audio.getId(), auth.getUser().getId());
    }
}
