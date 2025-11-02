package ru.kpfu.itis.service.analyze.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.model.TransactionEntity;
import ru.kpfu.itis.repository.AnalyzeRepository;
import ru.kpfu.itis.service.analyze.AnalyzeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AnalyzeServiceImpl implements AnalyzeService {

    private final AnalyzeRepository analyzeRepository;

    @Override
    public List<ExpenseDto> getMostExpenseCategoryByPeriod(UUID userId, LocalDate start, LocalDate end) {
        List<ExpenseCategoryEntity> expenses = analyzeRepository.findMostExpenseCategory(userId, start, end);
        List<ExpenseDto> result = new ArrayList<>();
        if (!expenses.isEmpty()) {
            for (ExpenseCategoryEntity expense : expenses) {
                result.add(ExpenseDto.builder()
                        .name(expense.getName())
                        .sum(expense.getTotalAmount())
                        .userId(userId)
                        .icon(expense.getIcon())
                        .build());
            }
        }
        return result;
    }

    @Override
    public List<IncomeDto> getMostIncomeCategoryByPeriod(UUID userId, LocalDate start, LocalDate end) {
        List<IncomeCategoryEntity> incomes = analyzeRepository.findMostIncomeCategory(userId, start, end);
        List<IncomeDto> result = new ArrayList<>();
        if (!incomes.isEmpty()) {
            for (IncomeCategoryEntity income : incomes) {
                result.add(IncomeDto.builder()
                        .name(income.getName())
                        .sum(income.getTotalAmount())
                        .userId(userId)
                        .icon(income.getIcon())
                        .build());
            }
        }
        return result;
    }

    @Override
    public List<TransactionDto> getLastFiveTransactions(UUID userId) {
        List<TransactionEntity> transactions = analyzeRepository.findLastTransactions(userId);
        List<TransactionDto> result = new ArrayList<>();
        if (!transactions.isEmpty()) {
            for (TransactionEntity transaction : transactions) {
                result.add(TransactionDto.builder()
                                .title(transaction.getTitle())
                                .type(transaction.getType())
                                .sum(transaction.getSum())
                                .date(transaction.getDate())
                                .build());
            }
        }
        return result;
    }

    @Override
    public List<TransactionDto> getLastFiveExpenseTransactions(UUID userId) {
        List<TransactionEntity> transactions = analyzeRepository.findLastExpenseTransactions(userId);
        List<TransactionDto> result = new ArrayList<>();
        if (!transactions.isEmpty()) {
            for (TransactionEntity transaction : transactions) {
                result.add(TransactionDto.builder()
                        .title(transaction.getTitle())
                        .type(transaction.getType())
                        .sum(transaction.getSum())
                        .date(transaction.getDate())
                        .build());
            }
        }
        return result;
    }

    @Override
    public List<TransactionDto> getLastFiveIncomeTransactions(UUID userId) {
        List<TransactionEntity> transactions = analyzeRepository.findLastIncomeTransactions(userId);
        List<TransactionDto> result = new ArrayList<>();
        if (!transactions.isEmpty()) {
            for (TransactionEntity transaction : transactions) {
                result.add(TransactionDto.builder()
                        .title(transaction.getTitle())
                        .type(transaction.getType())
                        .sum(transaction.getSum())
                        .date(transaction.getDate())
                        .build());
            }
        }
        return result;
    }

    @Override
    public Double getTotalExpensesByPeriod(UUID userId, LocalDate start, LocalDate end) {
        return analyzeRepository.findTotalExpensesByPeriod(userId, start, end);
    }

    @Override
    public Double getPercentageChange(double currentTotal, double previousTotal) {
        if (previousTotal != 0) {
            return ((currentTotal - previousTotal) / previousTotal) * 100;
        }
        return 0.0;
    }
}
