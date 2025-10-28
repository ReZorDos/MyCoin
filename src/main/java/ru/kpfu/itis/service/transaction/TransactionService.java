package ru.kpfu.itis.service.transaction;

import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.response.TransactionResponse;
import ru.kpfu.itis.model.TransactionEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionResponse createExpenseTransaction(TransactionDto transaction);

    TransactionResponse createIncomeTransaction(TransactionDto transaction);

    List<TransactionEntity> getAllTransactionsOfUser(UUID userId);

}
