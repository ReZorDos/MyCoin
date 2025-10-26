package ru.kpfu.itis.service.transaction;

import ru.kpfu.itis.dto.FieldErrorDto;

import java.util.List;

public interface TransactionDataValidation {

    List<FieldErrorDto> validateTitle(String title);
    List<FieldErrorDto> validateSum(Double sum);

}
