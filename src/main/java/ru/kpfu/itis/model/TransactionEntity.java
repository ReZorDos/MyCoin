package ru.kpfu.itis.model;

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
public class TransactionEntity {

    private UUID id;
    private UUID userId;
    private UUID savingGoalId;
    private UUID expenseCategoryId;
    private UUID incomeCategoryId;
    private String title;
    private String type;
    private Timestamp date;
    private double sum;
    private String expenseCategoryName;
    private String incomeCategoryName;

}
