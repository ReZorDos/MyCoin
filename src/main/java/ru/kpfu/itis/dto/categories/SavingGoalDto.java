package ru.kpfu.itis.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SavingGoalDto {

    private String name;
    private String title;
    private double total_amount;
    private double current_amount;
    private UUID userId;

}
