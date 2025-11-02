package ru.kpfu.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartDto {

    private UUID userId;
    private String chartType;
    private LocalDate startDate;
    private LocalDate endDate;
}
