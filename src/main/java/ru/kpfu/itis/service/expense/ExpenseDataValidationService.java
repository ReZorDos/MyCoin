package ru.kpfu.itis.service.expense;

import ru.kpfu.itis.dto.FieldErrorDto;

import java.util.List;

public interface ExpenseDataValidationService {

    List<FieldErrorDto> validateName(String name);
}
