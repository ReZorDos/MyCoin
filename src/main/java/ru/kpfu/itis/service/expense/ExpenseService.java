package ru.kpfu.itis.service.expense;

import ru.kpfu.itis.dto.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse createExpenseCategory(ExpenseDto request);

    List<String> getAvailableIcons(String iconsPath);

}
