package ru.kpfu.itis.repository;

import ru.kpfu.itis.model.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<UserEntity> findById(UUID uuid);
    List<UserEntity> findAllUsers();
    boolean deleteById(UUID uuid);
    UserEntity updateById(UUID uuid, UserEntity userEntity);
    UserEntity save(UserEntity userEntity);
    Optional<UserEntity> findByEmail(String email);
    void updateUserBalance(UUID userId, String type, double sum);
    double getUserBalance(UUID userId);

}
