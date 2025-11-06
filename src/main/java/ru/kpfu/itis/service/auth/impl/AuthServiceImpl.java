package ru.kpfu.itis.service.auth.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.request.SignInRequest;
import ru.kpfu.itis.dto.request.SignUpRequest;
import ru.kpfu.itis.dto.response.AuthResponse;
import ru.kpfu.itis.model.UserEntity;
import ru.kpfu.itis.repository.UserRepository;
import ru.kpfu.itis.service.auth.AuthDataValidationService;
import ru.kpfu.itis.service.auth.AuthService;
import ru.kpfu.itis.service.auth.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthDataValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signIn(SignInRequest request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateEmail(request.getEmail()));
        errors.addAll(validationService.validatePassword(request.getPassword()));

        if (!errors.isEmpty()) return fail(errors);

        Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            errors.add(new FieldErrorDto("Почта:", "не найдена"));
            return fail(errors);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            errors.add(new FieldErrorDto("Пароль:", "неверный"));
            return fail(errors);
        }

        return ok(user.get().getUuid());
    }

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateEmail(request.getEmail()));
        errors.addAll(validationService.validateNickName(request.getNickname()));
        errors.addAll(validationService.validatePassword(request.getPassword()));

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            errors.add(new FieldErrorDto("Почта", "уже используется"));
            return fail(errors);
        }

        userRepository.save(UserEntity.builder()
                        .email(request.getEmail())
                        .nickname(request.getNickname())
                        .password(passwordEncoder.encode(request.getPassword()))
                .build());

        return ok();
    }

    private AuthResponse fail(List<FieldErrorDto> errors) {
        return AuthResponse.builder()
                .success(false)
                .errors(errors)
                .build();
    }

    private AuthResponse ok(UUID userId) {
        return AuthResponse.builder()
                .success(true)
                .idUser(userId)
                .build();
    }

    private AuthResponse ok() {
        return AuthResponse.builder()
                .success(true)
                .build();
    }
}
