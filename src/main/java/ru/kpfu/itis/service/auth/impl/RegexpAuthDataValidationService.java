package ru.kpfu.itis.service.auth.impl;

import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.service.auth.AuthDataValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegexpAuthDataValidationService implements AuthDataValidationService {

    @Override
    public List<FieldErrorDto> validateEmail(String email) {
        List<FieldErrorDto> listOfErrors = new ArrayList<>();
        if (Objects.isNull(email)) {
            listOfErrors.add(new FieldErrorDto("email", "email is required"));
        } else {
            if (email.length() < 5) {
                listOfErrors.add(new FieldErrorDto("email", "email too short"));
            }
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                listOfErrors.add(new FieldErrorDto("email", "email format is invalid"));
            }
        }
        return listOfErrors;
    }

    @Override
    public List<FieldErrorDto> validateNickName(String nickname) {
        List<FieldErrorDto> listOfErrors = new ArrayList<>();
        if (Objects.isNull(nickname)) {
            listOfErrors.add(new FieldErrorDto("nickname", "nickname is required"));
        } else {
            if (nickname.length() < 4) {
                listOfErrors.add(new FieldErrorDto("nickname", "nickname is too short"));
            }
        }
        return listOfErrors;
    }

    @Override
    public List<FieldErrorDto> validatePassword(String password) {
        List<FieldErrorDto> listOfErrors = new ArrayList<>();
        if (Objects.isNull(password)) {
            listOfErrors.add(new FieldErrorDto("password", "password is required"));
        } else {
            if (password.length() < 8) {
                listOfErrors.add(new FieldErrorDto("password", "password too short"));
            }
            if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$")) {
                listOfErrors.add(new FieldErrorDto("password", "password format is invalid"));
            }
        }
        return listOfErrors;
    }
}
