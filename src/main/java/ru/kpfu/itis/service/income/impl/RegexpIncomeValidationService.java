package ru.kpfu.itis.service.income.impl;

import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.service.income.IncomeDataValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegexpIncomeValidationService implements IncomeDataValidation {

    @Override
    public List<FieldErrorDto> validateName(String name) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(name)) {
            errors.add(new FieldErrorDto("name", "Name is invalid"));
        } else {
            if (name.length() < 3) {
                errors.add(new FieldErrorDto("name", "name is too short"));
            }
        }
        return errors;
    }
}
