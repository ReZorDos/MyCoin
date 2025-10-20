package ru.kpfu.itis.service;

import ru.kpfu.itis.dto.FieldErrorDto;

import java.util.List;

public interface AuthDataValidationService {

    List<FieldErrorDto> validateEmail(String email);
    List<FieldErrorDto> validateNickName(String nickname);
    List<FieldErrorDto> validatePassword(String password);

}
