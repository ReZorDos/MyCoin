package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.repository.ChartRepository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ChartRepositoryImpl implements ChartRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String SQL_GET_EXPENSE_BY_CATEGORY = """
            select ec.name, coalesce(sum(t.sum), 0) as total
            from expense_category ec
            left join transaction t on ec.id = t.expense_category_id
            where ec.user_id = ?
                and t.date >= ?
                and t.date < ?
                and t.type = 'EXPENSE'
            group by ec.id, ec.name
            order by total desc
            """;

    private final String SQL_GET_INCOME_BY_CATEGORY = """
            select ic.name, coalesce(sum(t.sum), 0) as total
            from income_category ic
            left join transaction t on ic.id = t.income_category_id
            where ic.user_id = ?
                and t.date >= ?
                and t.date < ?
                and t.type = 'INCOME'
            group by ic.id, ic.name
            order by total desc
            """;
    private final String SQL_GET_INCOME_EXPENSE_BY_MONTH = """
            select 
                to_char(t.date, 'YYYY-MM') as month,
                coalesce(sum(case when t.type = 'INCOME' then t.sum else 0 end), 0) as income_total,
                coalesce(sum(case when t.type = 'EXPENSE' then t.sum else 0 end), 0) as expense_total
            from transaction t
            where t.user_id = ?
                and t.date is not null
                and (t.date >= ? or ? is null)
                and (t.date < ? or ? is null)
            group by to_char(t.date, 'YYYY-MM')
            order by to_char(t.date, 'YYYY-MM') asc
            """;

    @Override
    public Map<String, Number> getExpensesByCategory(UUID userId, LocalDate start, LocalDate end) {
        return jdbcTemplate.query(SQL_GET_EXPENSE_BY_CATEGORY,
                (ResultSet rs) -> {
                    Map<String, Number> result = new HashMap<>();
                    while (rs.next()) {
                        String name = rs.getString("name");
                        double total = rs.getDouble("total");
                        result.put(name, total);
                    }
                    return result;
                }, userId, start, end);
    }

    @Override
    public Map<String, Number> getIncomesByCategory(UUID userId, LocalDate start, LocalDate end) {
        return jdbcTemplate.query(SQL_GET_INCOME_BY_CATEGORY,
                (ResultSet rs) -> {
                    Map<String, Number> result = new HashMap<>();
                    while (rs.next()) {
                        String name = rs.getString("name");
                        double total = rs.getDouble("total");
                        result.put(name, total);
                    }
                    return result;
                }, userId, start, end);
    }

    @Override
    public Map<String, Map<String, Number>> getIncomeExpenseByMonth(UUID userId, LocalDate start, LocalDate end) {
        return jdbcTemplate.query(SQL_GET_INCOME_EXPENSE_BY_MONTH,
                (ResultSet rs) -> {
                    Map<String, Map<String, Number>> result = new HashMap<>();
                    Map<String, Number> incomeData = new LinkedHashMap<>();
                    Map<String, Number> expenseData = new LinkedHashMap<>();

                    while (rs.next()) {
                        String month = rs.getString("month");
                        double income = rs.getDouble("income_total");
                        double expense = rs.getDouble("expense_total");

                        incomeData.put(month, income);
                        expenseData.put(month, expense);
                    }
                    result.put("Income", incomeData);
                    result.put("Expense", expenseData);
                    return result;
                }, userId, start, start, end, end);
    }
}