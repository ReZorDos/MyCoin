package ru.kpfu.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseCategoryEntity {

    private UUID id;
    private String name;
    private double totalAmount;
    private UUID userId;
    private String icon;
    private Timestamp createdAt;

}
