package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.repository.IncomeCategoryRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class IncomeCategoryRepositoryImpl implements IncomeCategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final IncomeRowMapper rowMapper = new IncomeRowMapper();
    private static final String SQL_DELETE_BY_ID = "delete from income_category where id = ?";
    private static final String SQL_FIND_BY_ID = "select * from income_category where id = ?";
    private static final String SQL_FIND_ALL_CATEGORIES_BY_USER_ID = "select * from income_category where user_id = ? order by created_at asc";
    private static final String SQL_UPDATE_TOTAL_AMOUNT = "update income_category set total_amount = total_amount + ? where id = ?";
    private static final String SQL_UPDATE_BY_ID = """
            update income_category
            set name = ?, icon = ?
            where id = ?
            """;
    private static final String SQL_FIND_BY_USER_AND_INCOME_ID = """
           select * from income_category
           where id = ? and user_id = ?
           """;
    private static final String SQL_SAVE_INCOME_CATEGORY = """
            insert into income_category (name, user_id, icon)
            values (?, ?, ?)
            """;

    @Override
    public Optional<IncomeCategoryEntity> findByUserIdAndIncomeId(UUID incomeUUID, UUID userUUID) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_USER_AND_INCOME_ID, rowMapper, incomeUUID, userUUID));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<IncomeCategoryEntity> findAllIncomeCategoriesByIdUser(UUID uuid) {
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
    public IncomeCategoryEntity updateById(UUID uuid, IncomeCategoryEntity incomeCategory) {
        Optional<IncomeCategoryEntity> income = findById(uuid);
        if (income.isPresent()) {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    incomeCategory.getName(),
                    incomeCategory.getIcon(),
                    uuid);
            return findById(uuid).get();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void save(IncomeDto incomeCategory) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_INCOME_CATEGORY, new String[]{"id"});
            ps.setString(1, incomeCategory.getName());
            ps.setObject(2, incomeCategory.getUserId());
            ps.setString(3, incomeCategory.getIcon());
            return ps;
        }, keyHolder);
    }

    @Override
    public Optional<IncomeCategoryEntity> findById(UUID uuid) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, uuid));
        } catch (EmptyResultDataAccessException e) {
            Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public IncomeCategoryEntity updateTotalSum(UUID incomeId, Double transactionSum) {
        Optional<IncomeCategoryEntity> income = findById(incomeId);
        if (income.isPresent()) {
            jdbcTemplate.update(SQL_UPDATE_TOTAL_AMOUNT,
                    transactionSum,
                    incomeId);
            return findById(incomeId).get();
        }
        throw new IllegalArgumentException();
    }

    private static final class IncomeRowMapper implements RowMapper<IncomeCategoryEntity> {

        @Override
        public IncomeCategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return IncomeCategoryEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .totalAmount(rs.getDouble("total_amount"))
                    .icon(rs.getString("icon"))
                    .createdAt(rs.getTimestamp("created_at"))
                    .build();
        }
    }
}
