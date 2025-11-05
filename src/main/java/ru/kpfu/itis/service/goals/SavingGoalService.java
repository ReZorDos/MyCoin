package ru.kpfu.itis.service.goals;

import ru.kpfu.itis.dto.categories.SavingGoalDto;
import ru.kpfu.itis.dto.response.SavingGoalResponse;
import ru.kpfu.itis.model.SavingGoalDistribution;
import ru.kpfu.itis.model.SavingGoalEntity;

import java.util.List;
import java.util.UUID;

public interface SavingGoalService {

    SavingGoalResponse createSavingGoal(SavingGoalDto request);

    boolean deleteSavingGoal(UUID savingGoalId);

    SavingGoalEntity getSavingGoalById(UUID savingGoalId);

    SavingGoalResponse updateSavingGoal(UUID saveGoalId, SavingGoalEntity request, UUID userId);

    List<SavingGoalEntity> getAllSavingGoalsByIdUser(UUID userId);

    List<SavingGoalEntity> getCompletedGoals(UUID userId);

    List<SavingGoalDistribution> makeDistributionByGoals(String[] saveGoalsIds, String[] amounts);

    SavingGoalResponse addToCurrentAmount(UUID goalId, Double amountToAdd, UUID userId);

}
