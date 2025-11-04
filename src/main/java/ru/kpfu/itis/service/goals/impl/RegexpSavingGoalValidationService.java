package ru.kpfu.itis.service.goals.impl;

import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.service.goals.SavingGoalDataValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegexpSavingGoalValidationService implements SavingGoalDataValidation {
    @Override
    public List<FieldErrorDto> validateName(String name) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(name)) {
            errors.add(new FieldErrorDto("name", "name is invalid"));
        } else {
            if (name.length() < 2) {
                errors.add(new FieldErrorDto("name", "name is too short"));
            }
        }
        return errors;
    }

    @Override
    public List<FieldErrorDto> validateTitle(String title) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(title)) {
            errors.add(new FieldErrorDto("title", "title is invalid"));
        } else {
            if (title.length() < 2) {
                errors.add(new FieldErrorDto("title", "title is too short"));
            }
        }
        return errors;
    }

    @Override
    public List<FieldErrorDto> validateAmount(Double amount) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(amount)) {
            errors.add(new FieldErrorDto("amount", "amount is invalid"));
        } else {
            if (amount < 0) {
                errors.add(new FieldErrorDto("amount", "amount is less than null"));
            }
        }
        return errors;
    }
}
