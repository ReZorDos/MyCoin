package ru.kpfu.itis.repository;

import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.SavingGoalDistribution;
import ru.kpfu.itis.model.TransactionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

    void saveExpenseTransaction(TransactionDto expenseTransaction);

    TransactionEntity saveIncomeTransaction(TransactionDto incomeTransaction);

    void saveSavingGoalDistribution(UUID transactionId, UUID saveGoalId, Double amount);

    Optional<TransactionEntity> findTransactionById(UUID transactionId);

    List<TransactionEntity> findTransactionsWithPagination(UUID userId, int offset, int limit);

    int countAllTransactionsOfUser(UUID userId);

}
