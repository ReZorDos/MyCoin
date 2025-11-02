package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.model.TransactionEntity;
import ru.kpfu.itis.model.UserEntity;
import ru.kpfu.itis.repository.AnalyzeRepository;
import ru.kpfu.itis.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
public class AnalyzeRepositoryImpl implements AnalyzeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionRowMapper transactionRowMapper = new TransactionRowMapper();
    private final ExpenseRowMapper expenseRowMapper = new ExpenseRowMapper();
    private final IncomeRowMapper incomeRowMapper = new IncomeRowMapper();
    private final UserRepository userRepository;
    private final static String SQL_FIND_LAST_TRANSACTIONS = """
            select *
            from transaction
            where user_id = ?
            order by date DESC
            limit 5
            """;
    private final static String SQL_FIND_LAST_EXPENSE_TRANSACTIONS = """
            select *
            from transaction
            where user_id = ?
                and type = 'EXPENSE'
            order by date DESC
            limit 5
            """;
    private final static String SQL_FIND_LAST_INCOME_TRANSACTIONS = """
            select *
            from transaction
            where user_id = ?
                and type = 'INCOME'
            order by date DESC
            limit 5
            """;
    private final static String SQL_FIND_MOST_EXPENSE_CATEGORY = """
        select ec.name, coalesce(sum(t.sum), 0) as total, ec.icon
        from transaction t
        left join expense_category ec on ec.id = t.expense_category_id
        where t.user_id = ?
            and t.date >= ?
            and t.date < ?
            and t.type = 'EXPENSE'
        group by ec.id, ec.name, ec.icon
        order by total desc
        limit 5
        """;
    private final static String SQL_FIND_MOST_INCOME_CATEGORY = """
        select ic.name, coalesce(sum(t.sum), 0) as total, ic.icon
        from transaction t
        left join income_category ic on ic.id = t.income_category_id
        where t.user_id = ?
            and t.date >= ?
            and t.date < ?
            and t.type = 'INCOME'
        group by ic.id, ic.name, ic.icon
        order by total desc
        limit 5
        """;
    private final static String SQL_FIND_TOTAL_EXPENSES_BY_PERIOD = """
            select coalesce(sum(t.sum), 0) as total
            from transaction t
            where t.user_id = ?
                and t.date >= ?
                and t.date < ?
                and t.type = 'EXPENSE'
            """;
    private final static String SQL_FIND_TOTAL_INCOMES_BY_PERIOD = """
            select coalesce(sum(t.sum), 0) as total
            from transaction t
            where t.user_id = ?
                and t.date >= ?
                and t.date < ?
                and t.type = 'INCOME'
            """;

    @Override
    public List<ExpenseCategoryEntity> findMostExpenseCategory(UUID userId, LocalDate start, LocalDate end) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return jdbcTemplate.query(SQL_FIND_MOST_EXPENSE_CATEGORY,
                    expenseRowMapper,
                    userId, start, end);
        } else {
            return List.of();
        }
    }

    @Override
    public List<IncomeCategoryEntity> findMostIncomeCategory(UUID userId, LocalDate start, LocalDate end) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return jdbcTemplate.query(SQL_FIND_MOST_INCOME_CATEGORY,
                    incomeRowMapper,
                    userId, start, end);
        } else {
            return List.of();
        }
    }

    @Override
    public List<TransactionEntity> findLastTransactions(UUID userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return jdbcTemplate.query(SQL_FIND_LAST_TRANSACTIONS,
                    transactionRowMapper,
                    userId);
        } else {
            return List.of();
        }
    }

    @Override
    public List<TransactionEntity> findLastExpenseTransactions(UUID userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return jdbcTemplate.query(SQL_FIND_LAST_EXPENSE_TRANSACTIONS,
                    transactionRowMapper,
                    userId);
        } else {
            return List.of();
        }
    }

    @Override
    public List<TransactionEntity> findLastIncomeTransactions(UUID userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return jdbcTemplate.query(SQL_FIND_LAST_INCOME_TRANSACTIONS,
                    transactionRowMapper,
                    userId);
        } else {
            return List.of();
        }
    }

    @Override
    public Double findTotalExpensesByPeriod(UUID userId, LocalDate start, LocalDate end) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return jdbcTemplate.queryForObject(SQL_FIND_TOTAL_EXPENSES_BY_PERIOD,
                    Double.class,
                    userId, start, end);
        } else {
            return 0.0;
        }
    }

    @Override
    public Double findTotalIncomesByPeriod(UUID userId, LocalDate start, LocalDate end) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return jdbcTemplate.queryForObject(SQL_FIND_TOTAL_INCOMES_BY_PERIOD,
                    Double.class,
                    userId, start, end);
        } else {
            return 0.0;
        }
    }

    private static final class TransactionRowMapper implements RowMapper<TransactionEntity> {

        @Override
        public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return TransactionEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .title(rs.getString("title"))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .savingGoalId(getUUIDOrNull(rs, "saving_goal_id"))
                    .expenseCategoryId(getUUIDOrNull(rs, "expense_category_id"))
                    .incomeCategoryId(getUUIDOrNull(rs, "income_category_id"))
                    .type(rs.getString("type"))
                    .date(rs.getTimestamp("date"))
                    .sum(rs.getDouble("sum"))
                    .build();
        }

        private UUID getUUIDOrNull(ResultSet rs, String columnName) throws SQLException {
            String value = rs.getString(columnName);
            return value != null ? UUID.fromString(value) : null;
        }
    }

    private final static class ExpenseRowMapper implements RowMapper<ExpenseCategoryEntity> {
        @Override
        public ExpenseCategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ExpenseCategoryEntity.builder()
                    .name(rs.getString("name"))
                    .totalAmount(rs.getDouble("total"))
                    .icon(rs.getString("icon"))
                    .build();
        }
    }

    private final static class IncomeRowMapper implements RowMapper<IncomeCategoryEntity> {
        @Override
        public IncomeCategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return IncomeCategoryEntity.builder()
                    .name(rs.getString("name"))
                    .totalAmount(rs.getDouble("total"))
                    .icon(rs.getString("icon"))
                    .build();
        }
    }

}
