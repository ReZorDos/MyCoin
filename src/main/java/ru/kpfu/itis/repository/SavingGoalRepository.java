package ru.kpfu.itis.repository;

import ru.kpfu.itis.dto.categories.SavingGoalDto;
import ru.kpfu.itis.model.SavingGoalEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SavingGoalRepository {

    Optional<SavingGoalEntity> findByUserIdAndSavingGoalId(UUID userid, UUID saveGoalId);

    List<SavingGoalEntity> findAllSavingGoalsByIdUser(UUID userId);

    boolean deleteById(UUID saveGoalId);

    SavingGoalEntity updateById(UUID uuid, SavingGoalEntity saveGoal);

    void save(SavingGoalDto saveGoal);

    Optional<SavingGoalEntity> findById(UUID saveGoalId);

    SavingGoalEntity updateCurrentAmount(UUID goalId, Double amount);

}
