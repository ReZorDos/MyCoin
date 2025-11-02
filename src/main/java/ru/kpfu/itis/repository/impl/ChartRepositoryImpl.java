package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kpfu.itis.repository.ChartRepository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
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

    private final String SQL_GET_EXPENSE_BY_MONTH = """
            select 
                TO_CHAR(t.date, 'YYYY-MM') as month,
                coalesce(SUM(t.sum), 0) as monthly_total
            from transaction t
            where t.user_id = ?
                and t.type = 'EXPENSE'
                and t.date is not null
                and t.date >= ?
                and t.date < ?
            group by TO_CHAR(t.date, 'YYYY-MM')
            having coalesce(SUM(t.sum), 0) > 0
            order by month asc
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
    public Map<String, Map<String, Number>> getExpenseByMonth(UUID userId, LocalDate start, LocalDate end) {
        Map<String, Number> monthlyData = jdbcTemplate.query(SQL_GET_EXPENSE_BY_MONTH,
                (ResultSet rs) -> {
                    Map<String, Number> result = new HashMap<>();
                    while (rs.next()) {
                        String month = rs.getString("month");
                        double total = rs.getDouble("monthly_total");
                        result.put(month, total);
                    }
                    return result;
                }, userId, start, end);

        Map<String, Map<String, Number>> result = new HashMap<>();
        Map<String, Number> totalExpenses = new HashMap<>();

        for (Map.Entry<String, Number> entry : monthlyData.entrySet()) {
            totalExpenses.put(entry.getKey(), entry.getValue());
        }

        result.put("Total Expenses", totalExpenses);
        return result;
    }
}