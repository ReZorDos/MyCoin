package ru.kpfu.itis.repository;

import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.model.TransactionEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    void saveExpenseTransaction(TransactionDto expenseTransaction);

    List<TransactionEntity> getAllTransactionsOfUser(UUID userId);


}
