package ru.kpfu.itis.service.expense;

import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseService {

    ExpenseResponse createExpenseCategory(ExpenseDto request);

    boolean deleteExpenseCategory(UUID uuid);

    ExpenseResponse updateExpenseCategory(UUID uuid, ExpenseCategoryEntity request, UUID userId);

    ExpenseCategoryEntity getCategoryById(UUID uuid);

    List<String> getAvailableIcons(String iconsPath);

    void updateExpenseCategoryTotal(UUID expenseId, Double transactionSum);

}
