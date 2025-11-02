package ru.kpfu.itis.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {

    private String name;
    private UUID userId;
    private String icon;
    private double sum;
    private Timestamp createdAt;

}
