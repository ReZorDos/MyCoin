package ru.kpfu.itis.service.chart;

import ru.kpfu.itis.dto.ChartDto;
import ru.kpfu.itis.model.ChartData;

public interface ChartService {

    ChartData getChartData(ChartDto request);

    ChartData createExpensePieChart(ChartDto request);

    ChartData createIncomePieChart(ChartDto request);

}