package ru.kpfu.itis.service.transaction;

import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.model.SavingGoalDistribution;

import java.util.List;

public interface TransactionDataValidation {

    List<FieldErrorDto> validateTitle(String title);
    List<FieldErrorDto> validateSum(Double sum);
    List<FieldErrorDto> validateDistributions(TransactionDto transaction);

}
