package ru.kpfu.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeCategoryEntity {

    private UUID id;
    private String name;
    private double totalAmount;
    private UUID userId;
    private String icon;

}
