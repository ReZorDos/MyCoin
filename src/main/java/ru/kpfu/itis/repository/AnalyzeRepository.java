package ru.kpfu.itis.repository;

import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.model.TransactionEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AnalyzeRepository {

    List<ExpenseCategoryEntity> findMostExpenseCategory(UUID userId, LocalDate start, LocalDate end);
    List<IncomeCategoryEntity> findMostIncomeCategory(UUID userId, LocalDate start, LocalDate end);

    List<TransactionEntity> findLastTransactions(UUID userId);
    List<TransactionEntity> findLastExpenseTransactions(UUID userId);
    List<TransactionEntity> findLastIncomeTransactions(UUID userId);

    Double findTotalExpensesByPeriod(UUID userId, LocalDate start, LocalDate end);
    Double findTotalIncomesByPeriod(UUID userId, LocalDate start, LocalDate end);

}
