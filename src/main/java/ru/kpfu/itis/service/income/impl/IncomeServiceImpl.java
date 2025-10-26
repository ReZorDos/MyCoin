package ru.kpfu.itis.service.income.impl;

import lombok.RequiredArgsConstructor;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.dto.response.IncomeResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.repository.IncomeCategoryRepository;
import ru.kpfu.itis.service.income.IncomeDataValidation;
import ru.kpfu.itis.service.income.IncomeService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeCategoryRepository incomeRepository;
    private final IncomeDataValidation validationService;

    @Override
    public IncomeResponse createIncomeCategory(IncomeDto request) {
        List<FieldErrorDto> errors = new ArrayList<>();
        errors.addAll(validationService.validateName(request.getName()));

        if (!errors.isEmpty()) {
            return fail(errors);
        }

        incomeRepository.save(IncomeDto.builder()
                .name(request.getName())
                .userId(request.getUserId())
                .icon(request.getIcon())
                .build());

        return ok();
    }

    @Override
    public boolean deleteIncomeCategory(UUID uuid) {
        return incomeRepository.deleteById(uuid);
    }

    @Override
    public IncomeCategoryEntity getCategoryById(UUID uuid) {
        return incomeRepository.findById(uuid).get();
    }

    @Override
    public IncomeCategoryEntity updateIncomeCategory(UUID uuid, IncomeCategoryEntity request) {
        return incomeRepository.updateById(uuid, request);
    }

    @Override
    public List<String> getAvailableIcons(String iconsPath) {
        List<String> icons = new ArrayList<>();
        File iconDir = new File(iconsPath);

        File[] files = iconDir.listFiles((dir, name) ->
            name.toLowerCase().endsWith(".png") ||
            name.toLowerCase().endsWith(".jpg") ||
            name.toLowerCase().endsWith("jpeg")
        );
        for (File file : files) {
            icons.add(file.getName());
        }
        return icons;
    }

    private IncomeResponse fail(List<FieldErrorDto> errors) {
        return IncomeResponse.builder()
                .success(false)
                .errors(errors)
                .build();
    }

    private IncomeResponse ok() {
        return IncomeResponse.builder()
                .success(true)
                .build();
    }
}
