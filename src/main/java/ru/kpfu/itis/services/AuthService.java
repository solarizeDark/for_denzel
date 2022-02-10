package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Auth;

public interface AuthService {

    Auth getByCookieValue(String value);

}
