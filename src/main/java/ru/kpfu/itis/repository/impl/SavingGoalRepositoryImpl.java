package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.dto.categories.SavingGoalDto;
import ru.kpfu.itis.model.SavingGoalEntity;
import ru.kpfu.itis.repository.SavingGoalRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RequiredArgsConstructor
public class SavingGoalRepositoryImpl implements SavingGoalRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SaveGoalRowMapper rowMapper = new SaveGoalRowMapper();

    private static final String SQL_FIND_BY_ID = "select * from save_goal where id = ?";
    private static final String SQL_FIND_ALL_GOALS_BY_USER_ID = "select * from save_goal where user_id = ? order by created_at asc";
    private static final String SQL_DELETE_BY_ID = "delete from save_goal where id = ?";
    private static final String SQL_UPDATE_CURRENT_AMOUNT = "update save_goal set current_amount = current_amount + ? where id = ?";
    private static final String SQL_FIND_BY_USER_ID_AND_GOAL_ID = """
            select *
            from save_goal
            where id = ? and user_id = ?
            """;
    private static final String SQL_UPDATE_BY_ID = """
            update save_goal
            set name = ?, title = ?, total_amount = ?, current_amount = ?
            where id = ?
            """;
    private static final String SQL_SAVE_GOAL = """
            insert into save_goal (name, title, total_amount, current_amount, user_id)
            values (?, ?, ?, ?, ?)
            """;

    @Override
    public Optional<SavingGoalEntity> findByUserIdAndExpenseId(UUID userUUID, UUID goalUUID) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_USER_ID_AND_GOAL_ID, rowMapper, goalUUID, userUUID));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<SavingGoalEntity> findAllSavingGoalsByIdUser(UUID userId) {
        try {
            return jdbcTemplate.query(SQL_FIND_ALL_GOALS_BY_USER_ID, rowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean deleteById(UUID goalId) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, goalId) == 1;
    }

    @Override
    public SavingGoalEntity updateById(UUID goalId, SavingGoalEntity saveGoal) {
        Optional<SavingGoalEntity> existingGoal = findById(goalId);
        if (existingGoal.isPresent()) {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    saveGoal.getName(),
                    saveGoal.getTitle(),
                    saveGoal.getTotal_amount(),
                    saveGoal.getCurrent_amount(),
                    goalId);
            return findById(goalId).get();
        }
        throw new IllegalArgumentException("Save goal not found with id: " + goalId);
    }

    @Override
    public void save(SavingGoalDto saveGoal) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_GOAL, new String[]{"id"});
            ps.setString(1, saveGoal.getName());
            ps.setString(2, saveGoal.getTitle());
            ps.setDouble(3, saveGoal.getTotal_amount());
            ps.setDouble(4, saveGoal.getCurrent_amount());
            ps.setObject(5, saveGoal.getUserId());
            return ps;
        }, keyHolder);
    }

    @Override
    public Optional<SavingGoalEntity> findById(UUID saveGoalId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, saveGoalId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public SavingGoalEntity updateCurrentAmount(UUID savingGoalId, Double amount) {
        Optional<SavingGoalEntity> goal = findById(savingGoalId);
        if (goal.isPresent()) {
            jdbcTemplate.update(SQL_UPDATE_CURRENT_AMOUNT,
                    amount,
                    savingGoalId);
            return findById(savingGoalId).get();
        }
        throw new IllegalArgumentException();
    }


    private static final class SaveGoalRowMapper implements RowMapper<SavingGoalEntity> {

        @Override
        public SavingGoalEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return SavingGoalEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .title(rs.getString("title"))
                    .total_amount(rs.getDouble("total_amount"))
                    .current_amount(rs.getDouble("current_amount"))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .build();
        }
    }
}