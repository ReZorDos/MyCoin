package ru.kpfu.itis.service.expense.impl;

import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.service.expense.ExpenseDataValidationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegexpExpenseValidationService implements ExpenseDataValidationService {

    @Override
    public List<FieldErrorDto> validateName(String name) {
        List<FieldErrorDto> listOfErrors = new ArrayList<>();
        if (Objects.isNull(name)) {
            listOfErrors.add(new FieldErrorDto("name", "name is invalid"));
        } else {
            if (name.length() < 2) {
                listOfErrors.add(new FieldErrorDto("name", "name is too short"));
            }
        }
        return listOfErrors;
    }
}
