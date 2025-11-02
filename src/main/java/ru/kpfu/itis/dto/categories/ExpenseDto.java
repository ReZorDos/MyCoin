package ru.kpfu.itis.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {

    private String name;
    private UUID userId;
    private String icon;
    private double sum;

}
