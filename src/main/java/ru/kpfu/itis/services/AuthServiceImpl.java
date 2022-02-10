package ru.kpfu.itis.services;

import ru.kpfu.itis.models.Auth;
import ru.kpfu.itis.repositories.AuthRepository;

public class AuthServiceImpl implements AuthService {

    private AuthRepository authRepository;

    public AuthServiceImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public Auth getByCookieValue(String value) {
        return authRepository.findByCookieValue(value);
    }
}
