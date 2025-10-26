package ru.kpfu.itis.repository;

import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseCategoryRepository {

    Optional<ExpenseCategoryEntity> findByUserIdAndExpenseId(UUID userUUID, UUID expenseUUID);
    List<ExpenseCategoryEntity> findAllCategoriesByIdUser(UUID uuid);
    boolean deleteById(UUID uuid);
    ExpenseCategoryEntity updateById(UUID uuid, ExpenseCategoryEntity expenseCategory);
    void save(ExpenseDto expenseCategory);
    Optional<ExpenseCategoryEntity> findById(UUID uuid);

    ExpenseCategoryEntity updateTotalSum(UUID expenseId, Double transactionSum);

}
