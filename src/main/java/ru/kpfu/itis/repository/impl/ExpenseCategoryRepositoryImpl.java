package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class ExpenseCategoryRepositoryImpl implements ExpenseCategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ExpenseCategoryRowMapper rowMapper = new ExpenseCategoryRowMapper();
    private static final String SQL_FIND_BY_ID = "select * from expense_category where id = ?";
    private static final String SQL_FIND_ALL_CATEGORIES_BY_USER_ID = "select * from expense_category where user_id = ?";
    private static final String SQL_DELETE_BY_ID = "delete from expense_category where id = ?";
    private static final String SQL_FIND_BY_USER_ID_AND_EXPENSE_ID = """
            select *
            from expense_category
            where id = ? and user_id = ?
            """;
    private static final String SQL_UPDATE_BY_ID = """
            update expense_category
            set name = ?, total_amount = ?, user_id = ?, icon = ?
            where id = ?
            """;
    private static final String SQL_SAVE_EXPENSE_CATEGORY = """
            insert into expense_category (name, user_id, icon)
            values (?, ?, ?)
            """;

    @Override
    public Optional<ExpenseCategoryEntity> findByUserIdAndExpenseId(UUID userUUID, UUID expenseUUID) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_USER_ID_AND_EXPENSE_ID, rowMapper, expenseUUID, userUUID));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ExpenseCategoryEntity> findAllCategoriesByIdUser(UUID uuid) {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_CATEGORIES_BY_USER_ID, rowMapper, uuid);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, uuid) == 1;
    }

    @Override
    public ExpenseCategoryEntity updateById(UUID uuid, ExpenseCategoryEntity expenseCategory) {
        Optional<ExpenseCategoryEntity> expense = findById(uuid);
        if (expense.isPresent()) {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    expenseCategory.getName(),
                    expenseCategory.getTotalAmount(),
                    expenseCategory.getUserId(),
                    expenseCategory.getIcon(),
                    uuid);
            return findById(uuid).get();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void save(ExpenseDto expenseCategory) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_EXPENSE_CATEGORY, new String[]{"id"});
            ps.setString(1, expenseCategory.getName());
            ps.setObject(2, expenseCategory.getUserId());
            ps.setString(3, expenseCategory.getIcon());
            return ps;
        }, keyHolder);
    }

    @Override
    public Optional<ExpenseCategoryEntity> findById(UUID uuid) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, uuid));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final class ExpenseCategoryRowMapper implements RowMapper<ExpenseCategoryEntity> {

        @Override
        public ExpenseCategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ExpenseCategoryEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .totalAmount(rs.getFloat("total_amount"))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .icon(rs.getString("icon"))
                    .build();
        }
    }
}
