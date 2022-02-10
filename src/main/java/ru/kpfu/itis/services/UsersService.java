package ru.kpfu.itis.services;

import ru.kpfu.itis.forms.LoginForm;
import ru.kpfu.itis.forms.UserForm;
import ru.kpfu.itis.models.User;

import javax.servlet.http.Cookie;

public interface UsersService {
    User register(UserForm userForm);
    Cookie signIn(LoginForm loginForm);
    User findUserByCookieValue(String cookieValue);
}
