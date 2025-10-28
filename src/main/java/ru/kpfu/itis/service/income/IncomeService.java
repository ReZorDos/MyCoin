package ru.kpfu.itis.service.income;

import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.dto.response.IncomeResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;

import java.util.List;
import java.util.UUID;

public interface IncomeService {

    IncomeResponse createIncomeCategory(IncomeDto request);

    boolean deleteIncomeCategory(UUID uuid);

    IncomeCategoryEntity getCategoryById(UUID uuid);

    IncomeCategoryEntity updateIncomeCategory(UUID uuid, IncomeCategoryEntity request);

    List<String> getAvailableIcons(String iconsPath);

    void updateIncomeCategoryTotal(UUID incomeId, Double transactionSum);
}
