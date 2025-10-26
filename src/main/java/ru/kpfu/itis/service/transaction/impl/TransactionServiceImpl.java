package ru.kpfu.itis.service.transaction.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.dto.response.TransactionResponse;
import ru.kpfu.itis.model.TransactionEntity;
import ru.kpfu.itis.repository.TransactionRepository;
import ru.kpfu.itis.service.transaction.TransactionDataValidation;
import ru.kpfu.itis.service.transaction.TransactionService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDataValidation validationService;

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
                .saveGoalId(transaction.getSaveGoalId())
                .userId(transaction.getUserId())
                .sum(transaction.getSum())
                .type("EXPENSE")
                .build());

        return ok();
    }

    @Override
    public List<TransactionEntity> getAllTransactionsOfUser(UUID userId) {
        return transactionRepository.getAllTransactionsOfUser(userId);
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
