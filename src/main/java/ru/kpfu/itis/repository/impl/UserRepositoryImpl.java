package ru.kpfu.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.config.JdbcConfig;
import ru.kpfu.itis.model.UserEntity;
import ru.kpfu.itis.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper rowMapper = new UserRowMapper();
    private static final String SQL_FIND_BY_ID = "select * from user_entity where id = ?";
    private static final String SQL_FIND_ALL_USERS = "select * from user_entity";
    private static final String SQL_DELETE_BY_ID = "delete from user_entity where id = ?";
    private static final String SQL_FIND_BY_EMAIL = "select * from user_entity where email = ?";
    private static final String SQL_UPDATE_USER_BALANCE = "update user_entity set balance = balance + ? where id = ?";
    private static final String SQL_GET_USER_BALANCE = "select balance from user_entity where id = ?";
    private static final String SQL_UPDATE_BY_ID = """
            update user_entity 
            set nickname = ?, password = ?, email = ? 
            where id = ?
            """;
    private static final String SQL_SAVE_USER = """
            insert into user_entity (nickname, password, email)
            values (?, ?, ?)
            """;

    @Override
    public Optional<UserEntity> findById(UUID uuid) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, uuid));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return jdbcTemplate.query(SQL_FIND_ALL_USERS, rowMapper);
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return jdbcTemplate.update(SQL_DELETE_BY_ID, uuid) == 1;
    }

    @Override
    public UserEntity updateById(UUID uuid, UserEntity userEntity) {
        Optional<UserEntity> user = findById(uuid);
        if (user.isPresent()) {
            jdbcTemplate.update(SQL_UPDATE_BY_ID,
                    userEntity.getNickname(),
                    userEntity.getPassword(),
                    userEntity.getEmail(),
                    uuid);
            return findById(uuid).get();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_SAVE_USER, new String[]{"id"});
            ps.setString(1, userEntity.getNickname());
            ps.setString(2, userEntity.getPassword());
            ps.setString(3, userEntity.getEmail());
            return ps;
        }, keyHolder);
        UUID uuid = (UUID) keyHolder.getKeys().get("id");
        return findById(uuid).get();
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, rowMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateUserBalance(UUID userId, String type, double sum) {
        double amount = type.equals("EXPENSE") ? -Math.abs(sum) : Math.abs(sum);
        jdbcTemplate.update(SQL_UPDATE_USER_BALANCE, amount, userId);
    }

    @Override
    public double getUserBalance(UUID userId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_GET_USER_BALANCE, Double.class, userId)).orElse(0.0);
        } catch (EmptyResultDataAccessException e) {
            return 0.0;
        }
    }

    private static final class UserRowMapper implements RowMapper<UserEntity> {

        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return UserEntity.builder()
                    .uuid(UUID.fromString(rs.getString("id")))
                    .nickname(rs.getString("nickname"))
                    .password(rs.getString("password"))
                    .email(rs.getString("email"))
                    .build();
        }
    }
}
