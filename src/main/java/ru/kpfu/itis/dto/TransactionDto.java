package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
    private UUID saveGoalId;
    private String type;
    private Timestamp date;
    private String expenseCategoryName;
    private String incomeCategoryName;

}
