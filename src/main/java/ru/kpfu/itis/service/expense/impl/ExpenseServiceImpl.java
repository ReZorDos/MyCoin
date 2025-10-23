package ru.kpfu.itis.service.expense.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;
import ru.kpfu.itis.service.expense.ExpenseDataValidationService;
import ru.kpfu.itis.service.expense.ExpenseService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseCategoryRepository expenseRepository;
    private final ExpenseDataValidationService validationService;

    @Override
    public ExpenseResponse createExpenseCategory(ExpenseDto request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateName(request.getName()));

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        expenseRepository.save(ExpenseCategoryEntity.builder()
                        .name(request.getName())
                        .userId(request.getUserId())
                        .icon(request.getIcon())
                        .build());

        return ok();
    }

    private ExpenseResponse fail(List<FieldErrorDto> errors) {
        return ExpenseResponse.builder()
                .success(false)
                .errors(errors)
                .build();
    }

    private ExpenseResponse ok() {
        return ExpenseResponse.builder()
                .success(true)
                .build();
    }
}
