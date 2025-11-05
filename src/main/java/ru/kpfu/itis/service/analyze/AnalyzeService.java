package ru.kpfu.itis.service.analyze;

import org.springframework.cglib.core.Local;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.categories.IncomeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AnalyzeService {

    List<ExpenseDto> getMostExpenseCategoryByPeriod(UUID userId, LocalDate start, LocalDate end);
    List<IncomeDto> getMostIncomeCategoryByPeriod(UUID userId, LocalDate start, LocalDate end);

    List<TransactionDto> getLastFiveTransactions(UUID userId);
    List<TransactionDto> getMostFiveExpenseTransactionsByPeriod(UUID userId, LocalDate start, LocalDate end);
    List<TransactionDto> getMostFiveIncomeTransactionsByPeriod(UUID userId, LocalDate start, LocalDate end);

    Double getTotalExpensesByPeriod(UUID userId, LocalDate start, LocalDate end);
    Double getTotalIncomesByPeriod(UUID userId, LocalDate start, LocalDate end);

    Double getPercentageChange(double currentTotal, double previousTotal);


}
