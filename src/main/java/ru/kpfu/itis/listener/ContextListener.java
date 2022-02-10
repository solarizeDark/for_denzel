package ru.kpfu.itis.listener;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream;
        try {
            inputStream = classLoader.getResourceAsStream("db.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl         (properties.getProperty("db.url"));
        hikariConfig.setDriverClassName (properties.getProperty("db.driver"));
        hikariConfig.setUsername        (properties.getProperty("db.username"));
        hikariConfig.setPassword        (properties.getProperty("db.password"));
        hikariConfig.setMaximumPoolSize (Integer.parseInt(properties.getProperty("db.hikari.max-pool-size")));
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        String diskPath = properties.getProperty("disk.audio");

        UsersRepository usersRepository = new UsersRepositoryImpl(dataSource);
        AuthRepository authRepository = new AuthRepostoryImpl(dataSource);
        UsersService usersService = new UsersServicesImpl(usersRepository, authRepository);

        FavoritesRepository favoritesRepository = new FavoritesRepositoryImpl(dataSource);
        FavoritesService favoritesService = new FavoritesServiceImpl(favoritesRepository);

        AudiosRepository audiosRepository = new AudiosRepositoryImpl(dataSource);
        AudiosService audiosService = new AudiosServiceImpl(audiosRepository, favoritesRepository, authRepository);

        AuthService authService = new AuthServiceImpl(authRepository);

        context.setAttribute("usersService", usersService);
        context.setAttribute("audiosService", audiosService);
        context.setAttribute("diskPath", diskPath);
        context.setAttribute("authService", authService);
        context.setAttribute("favoritesService", favoritesService);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
