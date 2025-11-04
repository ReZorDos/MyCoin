package ru.kpfu.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingGoalEntity {

    private UUID id;
    private String name;
    private String title;
    private double total_amount;
    private double current_amount;
    private UUID userId;

}
