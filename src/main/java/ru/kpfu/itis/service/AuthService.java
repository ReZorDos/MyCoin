package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.request.SignInRequest;
import ru.kpfu.itis.dto.request.SignUpRequest;
import ru.kpfu.itis.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse signIn(SignInRequest request);
    AuthResponse signUp(SignUpRequest request);

}
