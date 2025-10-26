package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
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
import java.util.UUID;

@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TransactionRowMapper rowMapper = new TransactionRowMapper();
    private final String SQL_GET_ALL_TRANSACTIONS_OF_USER = "select * from transaction where user_id = ?";
    private static final String SQL_SAVE_EXPENSE_TRANSACTION = """
            insert  into transaction (title, expense_category_id, saving_goal_id, user_id, sum, type)
            values (?, ?, ?, ?, ?, ?)
            """;

    @Override
    public void saveExpenseTransaction(TransactionDto expenseTransaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_EXPENSE_TRANSACTION, new String[]{"id"});
            ps.setString(1, expenseTransaction.getTitle());
            ps.setObject(2, expenseTransaction.getExpenseId());
            ps.setObject(3, expenseTransaction.getSaveGoalId());
            ps.setObject(4, expenseTransaction.getUserId());
            ps.setDouble(5, expenseTransaction.getSum());
            ps.setString(6, expenseTransaction.getType());
            return ps;
        }, keyHolder);

    }

    @Override
    public List<TransactionEntity> getAllTransactionsOfUser(UUID userId) {
        return jdbcTemplate.query(SQL_GET_ALL_TRANSACTIONS_OF_USER, rowMapper, userId);
    }

    private static final class TransactionRowMapper implements RowMapper<TransactionEntity> {

        @Override
        public TransactionEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return TransactionEntity.builder()
                    .title(rs.getString("id"))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .savingGoalId(getUUIDOrNull(rs, "saving_goal_id"))
                    .expenseCategoryId(getUUIDOrNull(rs, "expense_category_id"))
                    .incomeCategoryId(getUUIDOrNull(rs, "income_category_id"))
                    .type(rs.getString("type"))
                    .date(rs.getDate("date"))
                    .sum(rs.getDouble("sum"))
                    .build();
        }

        private UUID getUUIDOrNull(ResultSet rs, String columnName) throws SQLException {
            String value = rs.getString(columnName);
            return value != null ? UUID.fromString(value) : null;
        }
    }
}
