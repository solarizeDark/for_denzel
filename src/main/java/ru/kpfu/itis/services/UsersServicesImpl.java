package ru.kpfu.itis.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.forms.LoginForm;
import ru.kpfu.itis.forms.UserForm;
import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repositories.AuthRepository;
import ru.kpfu.itis.repositories.UsersRepository;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class UsersServicesImpl implements UsersService {

    private UsersRepository usersRepository;
    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;

    public UsersServicesImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UsersServicesImpl(UsersRepository usersRepository, AuthRepository authRepository) {
        this.usersRepository = usersRepository;
        this.authRepository = authRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(UserForm userForm) {

        User user = User.builder()
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .login(userForm.getLogin())
                .build();

        String passwordHash = passwordEncoder.encode(userForm.getPassword());

        user.setPasswordHash(passwordHash);

        return usersRepository.save(user);
    }

    @Override
    public Cookie signIn(LoginForm loginForm) {

        User user = usersRepository.findByLogin(loginForm.getLogin());

        if (user != null) {
            if (passwordEncoder.matches(loginForm.getPassword(), user.getPasswordHash())) {

                String cookieValue = UUID.randomUUID().toString();

                Auth auth = Auth.builder()
                        .user(user)
                        .cookieValue(cookieValue)
                        .build();
                authRepository.save(auth);

                Cookie cookie = new Cookie("auth", cookieValue);
                cookie.setMaxAge(10 * 60 * 60);
                return cookie;
            } else {
                System.out.println("Вход не выполнен!");
            }
        }
        return null;
    }

    @Override
    public User findUserByCookieValue(String cookieValue) {
        Auth auth = authRepository.findByCookieValue(cookieValue);
        if (auth != null) {
            return auth.getUser();
        } else {
            return null;
        }
    }
}
