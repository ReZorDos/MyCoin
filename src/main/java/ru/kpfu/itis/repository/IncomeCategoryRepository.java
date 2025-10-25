package ru.kpfu.itis.repository;

import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomeCategoryRepository {

    Optional<IncomeCategoryEntity> findByUserIdAndIncomeId(UUID userUUID, UUID incomeUUID);
    List<IncomeCategoryEntity> findAllCategoriesByIdUser(UUID uuid);
    boolean deleteById(UUID uuid);
    IncomeCategoryEntity updateById(UUID uuid, IncomeCategoryEntity incomeCategory);
    void save(IncomeDto incomeCategory);
    Optional<IncomeCategoryEntity> findById(UUID uuid);

}
