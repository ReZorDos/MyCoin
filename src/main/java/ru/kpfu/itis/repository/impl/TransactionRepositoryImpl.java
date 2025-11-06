package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.model.TransactionEntity;
import ru.kpfu.itis.repository.TransactionRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionRowMapper rowMapper = new TransactionRowMapper();
    private final TransactionWithCategoryRowMapper transactionWithCategoryRowMapper = new TransactionWithCategoryRowMapper();
    private static final String SQL_FIND_BY_ID = "select * from transaction where id = ?";
    private static final String SQL_COUNT_ALL_TRANSACTIONS_OF_USER = "select count(*) from transaction where user_id = ?";
    private static final String SQL_FIND_ALL_TRANSACTIONS_WITH_PAGINATION = """
        select t.*,
            ec.name as expense_category_name,
            ic.name as income_category_name
        from transaction t
        left join expense_category ec ON t.expense_category_id = ec.id
        left join income_category ic ON t.income_category_id = ic.id
        where t.user_id = ?
        order by t.date desc
        limit ?
        offset ?
            """;
    private static final String SQL_SAVE_SAVING_GOAL_DISTRIBUTION = """
            insert into transaction_saving_distribution (transaction_id, save_goal_id, amount)
            values (?, ?, ?)
            """;
    private static final String SQL_SAVE_EXPENSE_TRANSACTION = """
            insert  into transaction (title, expense_category_id, user_id, sum, type)
            values (?, ?, ?, ?, ?)
            """;
    private static final String SQL_SAVE_INCOME_TRANSACTION = """
            insert  into transaction (title, income_category_id, user_id, sum, type)
            values (?, ?, ?, ?, ?)
            """;

    @Override
    public void saveExpenseTransaction(TransactionDto expenseTransaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_EXPENSE_TRANSACTION, new String[]{"id"});
            ps.setString(1, expenseTransaction.getTitle());
            ps.setObject(2, expenseTransaction.getExpenseId());
            ps.setObject(3, expenseTransaction.getUserId());
            ps.setDouble(4, expenseTransaction.getSum());
            ps.setString(5, expenseTransaction.getType());
            return ps;
        }, keyHolder);
    }

    @Override
    public TransactionEntity saveIncomeTransaction(TransactionDto incomeTransaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_INCOME_TRANSACTION, new String[]{"id"});
            ps.setString(1, incomeTransaction.getTitle());
            ps.setObject(2, incomeTransaction.getIncomeId());
            ps.setObject(3, incomeTransaction.getUserId());
            ps.setDouble(4, incomeTransaction.getSum());
            ps.setString(5, incomeTransaction.getType());
            return ps;
        }, keyHolder);
        UUID uuid = (UUID) keyHolder.getKeys().get("id");
        return findTransactionById(uuid).get();
    }

    @Override
    public void saveSavingGoalDistribution(UUID transactionId, UUID saveGoalId, Double amount) {
        jdbcTemplate.update(SQL_SAVE_SAVING_GOAL_DISTRIBUTION, transactionId, saveGoalId, amount);
    }

    @Override
    public Optional<TransactionEntity> findTransactionById(UUID transactionId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, transactionId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<TransactionEntity> findTransactionsWithPagination(UUID userId, int offset, int limit) {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_TRANSACTIONS_WITH_PAGINATION,
                    transactionWithCategoryRowMapper, userId, limit, offset);
        } catch (EmptyResultDataAccessException e) {
            return List.of();
        }
    }

    @Override
    public int countAllTransactionsOfUser(UUID userId) {
        try {
            return jdbcTemplate.queryForObject(SQL_COUNT_ALL_TRANSACTIONS_OF_USER, Integer.class, userId);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    private static final class TransactionRowMapper implements RowMapper<TransactionEntity> {

        @Override
        public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return TransactionEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .title(rs.getString("title"))
                    .userId(UUID.fromString(rs.getString("user_id")))
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

    private static final class TransactionWithCategoryRowMapper implements RowMapper<TransactionEntity> {
        @Override
        public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return TransactionEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .title(rs.getString("title"))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .expenseCategoryId(getUUIDOrNull(rs, "expense_category_id"))
                    .incomeCategoryId(getUUIDOrNull(rs, "income_category_id"))
                    .type(rs.getString("type"))
                    .date(rs.getTimestamp("date"))
                    .sum(rs.getDouble("sum"))
                    .expenseCategoryName(rs.getString("expense_category_name"))
                    .incomeCategoryName(rs.getString("income_category_name"))
                    .build();
        }

        private UUID getUUIDOrNull(ResultSet rs, String columnName) throws SQLException {
            String value = rs.getString(columnName);
            return value != null ? UUID.fromString(value) : null;
        }
    }
}
