package ru.kpfu.itis.service.income;

import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.dto.response.IncomeResponse;

import java.util.List;

public interface IncomeService {

    IncomeResponse createIncomeCategory(IncomeDto request);

    List<String> getAvailableIcons(String iconsPath);
}
