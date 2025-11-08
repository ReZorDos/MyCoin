package ru.kpfu.itis.service.goals.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.categories.SavingGoalDto;
import ru.kpfu.itis.dto.response.SavingGoalResponse;
import ru.kpfu.itis.dto.SavingGoalDistribution;
import ru.kpfu.itis.model.SavingGoalEntity;
import ru.kpfu.itis.repository.SavingGoalRepository;
import ru.kpfu.itis.service.goals.SavingGoalDataValidation;
import ru.kpfu.itis.service.goals.SavingGoalService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class SavingGoalServiceImpl implements SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;
    private final SavingGoalDataValidation validationService;

    @Override
    public SavingGoalResponse createSavingGoal(SavingGoalDto request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateName(request.getName()));
        errors.addAll(validationService.validateTitle(request.getTitle()));
        errors.addAll(validationService.validateAmount(request.getTotal_amount()));

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        savingGoalRepository.save(SavingGoalDto.builder()
                .name(request.getName())
                .title(request.getTitle())
                .total_amount(request.getTotal_amount())
                .current_amount(request.getCurrent_amount())
                .userId(request.getUserId())
                .build());

        return ok();
    }

    @Override
    public boolean deleteSavingGoal(UUID savingGoalId) {
        return savingGoalRepository.deleteById(savingGoalId);
    }

    @Override
    public SavingGoalEntity getSavingGoalById(UUID userId, UUID savingGoalId) {
        Optional<SavingGoalEntity> goal = savingGoalRepository.findByUserIdAndSavingGoalId(userId, savingGoalId);
        if (goal.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return goal.get();
    }

    @Override
    public SavingGoalResponse updateSavingGoal(UUID saveGoalId, SavingGoalEntity request, UUID userId) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateName(request.getName()));
        errors.addAll(validationService.validateTitle(request.getTitle()));
        errors.addAll(validationService.validateAmount(request.getTotal_amount()));

        Optional<SavingGoalEntity> savingGoal = savingGoalRepository.findByUserIdAndSavingGoalId(userId, saveGoalId);
        if (savingGoal.isEmpty()) {
            errors.add(new FieldErrorDto("goal", "Saving goal not found or access denied"));
        }

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        SavingGoalEntity goalToUpdate = SavingGoalEntity.builder()
                .name(request.getName())
                .title(request.getTitle())
                .total_amount(request.getTotal_amount())
                .current_amount(savingGoal.get().getCurrent_amount())
                .build();

        savingGoalRepository.updateById(saveGoalId, goalToUpdate);
        return ok();
    }

    @Override
    public List<SavingGoalEntity> getAllSavingGoalsByIdUser(UUID userId) {
        return savingGoalRepository.findAllSavingGoalsByIdUser(userId);
    }

    @Override
    public SavingGoalResponse addToCurrentAmount(UUID goalId, Double amountToAdd, UUID userId) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateAmount(amountToAdd));

        Optional<SavingGoalEntity> existingGoal = savingGoalRepository.findByUserIdAndSavingGoalId(userId, goalId);

        SavingGoalEntity goal = existingGoal.get();
        double newAmount = goal.getCurrent_amount() + amountToAdd;

        savingGoalRepository.updateCurrentAmount(goalId, amountToAdd);
        return ok();
    }

    @Override
    public List<SavingGoalDistribution> makeDistributionByGoals(String[] saveGoalIds, String[] amounts) {
        List<SavingGoalDistribution> distributions = new ArrayList<>();
        if (saveGoalIds != null && amounts != null && saveGoalIds.length == amounts.length) {
            for (int i = 0; i < saveGoalIds.length; i++) {
                double amount = Double.parseDouble(amounts[i]);
                if (amount > 0) {
                    distributions.add(SavingGoalDistribution.builder()
                            .saveGoalId(UUID.fromString(saveGoalIds[i]))
                            .amount(amount)
                            .build());
                }
            }
        }
        return distributions;
    }

    private SavingGoalResponse fail(List<FieldErrorDto> errors) {
        return SavingGoalResponse.builder()
                .success(false)
                .errors(errors)
                .build();
    }

    private SavingGoalResponse ok() {
        return SavingGoalResponse.builder()
                .success(true)
                .build();
    }
}