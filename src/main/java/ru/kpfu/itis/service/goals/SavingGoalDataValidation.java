package ru.kpfu.itis.service.goals;

import ru.kpfu.itis.dto.FieldErrorDto;

import java.util.List;

public interface SavingGoalDataValidation {

    List<FieldErrorDto> validateName(String name);
    List<FieldErrorDto> validateTitle(String title);
    List<FieldErrorDto> validateAmount(Double amount);

}
