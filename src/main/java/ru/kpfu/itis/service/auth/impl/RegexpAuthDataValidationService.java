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
            listOfErrors.add(new FieldErrorDto("Почта:", "Требуется адрес электронной почты"));
        } else {
            if (email.length() < 5) {
                listOfErrors.add(new FieldErrorDto("Почта:", "Минимальная длина 5 символов"));
            }
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                listOfErrors.add(new FieldErrorDto("Почта:", "Формат должен быть ___@___.___"));
            }
        }
        return listOfErrors;
    }

    @Override
    public List<FieldErrorDto> validateNickName(String nickname) {
        List<FieldErrorDto> listOfErrors = new ArrayList<>();
        if (Objects.isNull(nickname)) {
            listOfErrors.add(new FieldErrorDto("Никнейм:", "Требуется никнейм"));
        } else {
            if (nickname.length() < 4) {
                listOfErrors.add(new FieldErrorDto("Никнейм:", "слишком короткий"));
            }
        }
        return listOfErrors;
    }

    @Override
    public List<FieldErrorDto> validatePassword(String password) {
        List<FieldErrorDto> listOfErrors = new ArrayList<>();
        if (Objects.isNull(password)) {
            listOfErrors.add(new FieldErrorDto("Пароль:", "Требуется пароль"));
        } else {
            if (password.length() < 8) {
                listOfErrors.add(new FieldErrorDto("Пароль:", "длина должна быть больше 8 символов"));
            }
            if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$")) {
                listOfErrors.add(new FieldErrorDto(
                        "Пароль:",
                        "должен содержать хотя бы одну заглавную букву, хотя бы одну строную букву и хотя бы одну цифру ")
                );
            }
        }
        return listOfErrors;
    }
}
