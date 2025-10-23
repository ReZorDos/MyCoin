package ru.kpfu.itis.service.expense;

import ru.kpfu.itis.dto.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;

public interface ExpenseService {

    ExpenseResponse createExpenseCategory(ExpenseDto request);

}
