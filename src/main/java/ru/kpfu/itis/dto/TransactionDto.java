package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kpfu.itis.model.SavingGoalDistribution;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {

    private String title;
    private double sum;
    private UUID userId;
    private UUID expenseId;
    private UUID incomeId;
    private String type;
    private Timestamp date;
    private String expenseCategoryName;
    private String incomeCategoryName;
    private double distributedAmount;
    private List<SavingGoalDistribution> distributions;

}
