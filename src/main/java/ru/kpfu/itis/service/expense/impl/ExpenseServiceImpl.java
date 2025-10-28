package ru.kpfu.itis.service.expense.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;
import ru.kpfu.itis.service.expense.ExpenseDataValidationService;
import ru.kpfu.itis.service.expense.ExpenseService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

        expenseRepository.save(ExpenseDto.builder()
                        .name(request.getName())
                        .userId(request.getUserId())
                        .icon(request.getIcon())
                        .build());

        return ok();
    }

    @Override
    public boolean deleteExpenseCategory(UUID uuid) {
        return expenseRepository.deleteById(uuid);
    }

    @Override
    public ExpenseResponse updateExpenseCategory(UUID uuid, ExpenseCategoryEntity request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateName(request.getName()));

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        expenseRepository.updateById(uuid, request);
        return ok();
    }

    @Override
    public ExpenseCategoryEntity getCategoryById(UUID uuid) {
        return expenseRepository.findById(uuid).get();
    }

    @Override
    public List<String> getAvailableIcons(String iconPath) {
        List<String> icons = new ArrayList<>();
        File iconDir = new File(iconPath);
        File[] files = iconDir.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".png") ||
                name.toLowerCase().endsWith(".jpg") ||
                name.toLowerCase().endsWith("jpeg")
        );
        for (File file : files) {
            icons.add(file.getName());
        }
        return icons;
    }

    @Override
    public void updateExpenseCategoryTotal(UUID expenseId, Double transactionSum) {
        expenseRepository.updateTotalSum(expenseId, transactionSum);
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
