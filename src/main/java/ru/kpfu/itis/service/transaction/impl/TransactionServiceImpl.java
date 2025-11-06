package ru.kpfu.itis.service.transaction.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.response.TransactionResponse;
import ru.kpfu.itis.dto.SavingGoalDistribution;
import ru.kpfu.itis.model.TransactionEntity;
import ru.kpfu.itis.repository.TransactionRepository;
import ru.kpfu.itis.service.goals.SavingGoalService;
import ru.kpfu.itis.service.transaction.TransactionDataValidation;
import ru.kpfu.itis.service.transaction.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDataValidation validationService;
    private final SavingGoalService savingGoalService;

    @Override
    public TransactionResponse createExpenseTransaction(TransactionDto transaction) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateTitle(transaction.getTitle()));
        errors.addAll(validationService.validateSum(transaction.getSum()));

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        transactionRepository.saveExpenseTransaction(TransactionDto.builder()
                .title(transaction.getTitle())
                .expenseId(transaction.getExpenseId())
                .userId(transaction.getUserId())
                .sum(transaction.getSum())
                .type("EXPENSE")
                .build());

        return ok();
    }

    @Override
    public TransactionResponse createIncomeTransaction(TransactionDto transaction) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateTitle(transaction.getTitle()));
        errors.addAll(validationService.validateSum(transaction.getSum()));
        errors.addAll(validationService.validateDistributions(transaction));

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        TransactionEntity transactionEntity =  transactionRepository.saveIncomeTransaction(TransactionDto.builder()
                .title(transaction.getTitle())
                .incomeId(transaction.getIncomeId())
                .userId(transaction.getUserId())
                .sum(transaction.getSum())
                .type("INCOME")
                .build());

        if (transaction.getDistributions() != null) {
            for (SavingGoalDistribution distribution : transaction.getDistributions()) {
                transactionRepository.saveSavingGoalDistribution(
                        transactionEntity.getId(),
                        distribution.getSaveGoalId(),
                        distribution.getAmount()
                );

                savingGoalService.addToCurrentAmount(
                        distribution.getSaveGoalId(),
                        distribution.getAmount(),
                        transaction.getUserId());
            }
        }

        return ok();
    }

    @Override
    public List<TransactionDto> getTransactionsWithPagination(UUID userId, int page, int size) {
        int offset = (page - 1) * size;
        return transactionRepository.findTransactionsWithPagination(userId, offset, size).stream()
                .map((transaction) -> TransactionDto.builder()
                        .title(transaction.getTitle())
                        .type(transaction.getType())
                        .sum(transaction.getSum())
                        .date(transaction.getDate())
                        .expenseCategoryName(transaction.getExpenseCategoryName())
                        .incomeCategoryName(transaction.getIncomeCategoryName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalTransactionsCount(UUID userId) {
        return transactionRepository.countAllTransactionsOfUser(userId);
    }

    @Override
    public int getIntParameter(String paramValue, String paramName, int defaultValue) {
        if (paramValue == null || paramValue.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private TransactionResponse fail(List<FieldErrorDto> errors) {
        return TransactionResponse.builder()
                .success(false)
                .errors(errors)
                .build();
    }

    private TransactionResponse ok() {
        return TransactionResponse.builder()
                .success(true)
                .build();
    }
}
