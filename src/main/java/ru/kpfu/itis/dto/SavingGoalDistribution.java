package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavingGoalDistribution {

    private UUID saveGoalId;
    private double amount;
    private String goalName;

}
