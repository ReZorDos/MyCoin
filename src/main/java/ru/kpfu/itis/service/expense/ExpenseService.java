package ru.kpfu.itis.service.expense;

import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    ExpenseResponse createExpenseCategory(ExpenseDto request);

    boolean deleteExpenseCategory(UUID uuid);

    List<String> getAvailableIcons(String iconsPath);

}
