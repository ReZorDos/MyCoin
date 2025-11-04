package ru.kpfu.itis.repository;

import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.model.SavingGoalDistribution;
import ru.kpfu.itis.model.TransactionEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {

    void saveExpenseTransaction(TransactionDto expenseTransaction);

    TransactionEntity saveIncomeTransaction(TransactionDto incomeTransaction);

    List<TransactionEntity> getAllTransactionsOfUser(UUID userId);

    void saveSavingGoalDistribution(UUID transactionId, UUID saveGoalId, Double amount);

    List<SavingGoalDistribution> findAllDistributionsByTransactionId(UUID transactionId);

    Optional<TransactionEntity> findTransactionById(UUID transactionId);

}
