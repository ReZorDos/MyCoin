package ru.kpfu.itis.service.transaction.impl;

import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.SavingGoalDistribution;
import ru.kpfu.itis.service.transaction.TransactionDataValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegexpTransactionValidationService implements TransactionDataValidation {
    @Override
    public List<FieldErrorDto> validateTitle(String title) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(title)) {
            errors.add(new FieldErrorDto("Название:", "Тербуется название"));
        } else {
            if (title.length() < 2) {
                errors.add(new FieldErrorDto("Название:", "слишком короткое"));
            }
            if (title.length() > 25) {
                errors.add(new FieldErrorDto("Название:", "не должно превышать 25 символов"));
            }
        }
        return errors;
    }

    @Override
    public List<FieldErrorDto> validateSum(Double sum) {
        List<FieldErrorDto> errors = new ArrayList<>();
        if (Objects.isNull(sum)) {
            errors.add(new FieldErrorDto("Сумма", "Требуется сумма"));
        } else {
            if (sum < 0) {
                errors.add(new FieldErrorDto("Сумма", "меньше нуля"));
            }
        }
        return errors;
    }

    @Override
    public List<FieldErrorDto> validateDistributions(TransactionDto transaction) {
        List<FieldErrorDto> errors = new ArrayList<>();
        double totalDistributed = transaction.getDistributions().stream()
                .mapToDouble(SavingGoalDistribution::getAmount)
                .sum();

        if (totalDistributed > transaction.getSum()) {
            errors.add(new FieldErrorDto("Распределения", "сумма  не может превышать сумму транзакции"));
        }
        return errors;
    }
}