package ru.kpfu.itis.service.income;

import ru.kpfu.itis.dto.FieldErrorDto;

import java.util.List;

public interface IncomeDataValidation {

    List<FieldErrorDto> validateName(String name);

}
