package ru.kpfu.itis.repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface ChartRepository {

    Map<String, Number> getExpensesByCategory(UUID userId, LocalDate start, LocalDate end);

    Map<String, Map<String, Number>> getExpenseByMonth(UUID userId, LocalDate start, LocalDate end);


}
