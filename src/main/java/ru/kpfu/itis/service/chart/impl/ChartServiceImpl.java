package ru.kpfu.itis.service.chart.impl;

import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ui.RectangleInsets;
import ru.kpfu.itis.dto.ChartDto;
import ru.kpfu.itis.model.ChartData;
import ru.kpfu.itis.repository.ChartRepository;
import ru.kpfu.itis.service.chart.ChartService;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ChartServiceImpl implements ChartService {

    private final ChartRepository chartRepository;

    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
    private static final Color TEXT_COLOR = new Color(60, 60, 60);
    private static final Color GRID_COLOR = new Color(220, 220, 220);
    private static final Color[] PIE_COLORS = {
            new Color(74, 144, 226),
            new Color(230, 85, 80),
            new Color(250, 180, 50),
            new Color(90, 200, 150),
            new Color(160, 100, 220),
            new Color(240, 130, 40),
            new Color(50, 180, 210),
            new Color(180, 100, 150)
    };
    private static final Color LINE_COLOR = new Color(74, 144, 226);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font LEGEND_FONT = new Font("Segoe UI", Font.PLAIN, 13);


    @Override
    public ChartData createExpensePieChart(ChartDto request) {
        Map<String, Number> map = chartRepository.getExpensesByCategory(
                request.getUserId(),
                request.getStartDate(),
                request.getEndDate()
        );

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (Map.Entry<String, Number> entry : map.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue().doubleValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                true,
                false
        );

        stylePieChart(chart);

        String chartImage = convertChartToBase64(chart, 800, 500);
        return ChartData.builder()
                .userId(request.getUserId())
                .imageBase64(chartImage)
                .build();
    }

    @Override
    public ChartData createIncomePieChart(ChartDto request) {
        Map<String, Number> map = chartRepository.getIncomesByCategory(
                request.getUserId(),
                request.getStartDate(),
                request.getEndDate()
        );

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (Map.Entry<String, Number> entry : map.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue().doubleValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                null,
                dataset,
                true,
                true,
                false
        );

        stylePieChart(chart);

        String chartImage = convertChartToBase64(chart, 800, 500);
        return ChartData.builder()
                .userId(request.getUserId())
                .imageBase64(chartImage)
                .build();
    }

    @Override
    public ChartData createIncomeExpenseLineChart(ChartDto request) {
        Map<String, Map<String, Number>> data = chartRepository.getIncomeExpenseByMonth(
                request.getUserId(),
                request.getStartDate(),
                request.getEndDate()
        );

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, Number> incomeData = data.get("Income");
        Map<String, Number> expenseData = data.get("Expense");

        List<String> sortedMonths = Stream.concat(incomeData.keySet().stream(), expenseData.keySet().stream())
                .distinct()
                .sorted()
                .toList();

        if (!sortedMonths.isEmpty()) {
            List<Double> incomeHistory = sortedMonths.stream()
                    .map(month -> incomeData.get(month).doubleValue())
                    .collect(Collectors.toList());

            List<Double> expenseHistory = sortedMonths.stream()
                    .map(month -> expenseData.get(month).doubleValue())
                    .collect(Collectors.toList());

            List<Double> incomeForecast = forecastLinear(incomeHistory, 3);
            List<Double> expenseForecast = forecastLinear(expenseHistory, 3);

            String lastActualMonth = sortedMonths.get(sortedMonths.size() - 1);
            LocalDate lastMonthDate = LocalDate.parse(lastActualMonth + "-01");

            Double lastIncome = incomeData.get(lastActualMonth).doubleValue();
            Double lastExpense = expenseData.get(lastActualMonth).doubleValue();

            List<String> allMonths = new ArrayList<>();
            List<Double> allIncome = new ArrayList<>();
            List<Double> allExpense = new ArrayList<>();

            for (String month : sortedMonths) {
                allMonths.add(month);
                allIncome.add(incomeData.get(month).doubleValue());
                allExpense.add(expenseData.get(month).doubleValue());
            }

            allMonths.add(lastActualMonth);
            allIncome.add(lastIncome);
            allExpense.add(lastExpense);

            for (int i = 0; i < 3; i++) {
                LocalDate forecastMonth = lastMonthDate.plusMonths(i + 1);
                String monthKey = forecastMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                allMonths.add(monthKey);
                allIncome.add(incomeForecast.get(i));
                allExpense.add(expenseForecast.get(i));
            }

            for (int i = 0; i < allMonths.size(); i++) {
                String month = allMonths.get(i);
                String formattedMonth = formatMonth(month);

                if (i <= sortedMonths.size()) {
                    dataset.addValue(allIncome.get(i), "Доходы", formattedMonth);
                    dataset.addValue(allExpense.get(i), "Расходы", formattedMonth);
                }

                if (i >= sortedMonths.size()) {
                    dataset.addValue(allIncome.get(i), "Доходы (прогноз)", formattedMonth);
                    dataset.addValue(allExpense.get(i), "Расходы (прогноз)", formattedMonth);
                }
            }
        } else {
            for (String month : sortedMonths) {
                Number incomeValue = incomeData.get(month);
                Number expenseValue = expenseData.get(month);
                if (incomeValue != null && incomeValue.doubleValue() > 0) {
                    dataset.addValue(incomeValue, "Доходы", formatMonth(month));
                }
                if (expenseValue != null && expenseValue.doubleValue() > 0) {
                    dataset.addValue(expenseValue, "Расходы", formatMonth(month));
                }
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Динамика доходов и расходов по месяцам",
                "Месяц",
                "Сумма (руб)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        styleLineChartWithForecast(chart);

        String chartImage = convertChartToBase64(chart, 800, 500);
        return ChartData.builder()
                .userId(request.getUserId())
                .imageBase64(chartImage)
                .build();
    }

    private String formatMonth(String month) {
        try {
            String[] parts = month.split("-");
            int year = Integer.parseInt(parts[0]);
            int monthNum = Integer.parseInt(parts[1]);

            String[] monthNames = {"Янв", "Фев", "Мар", "Апр", "Май", "Июн",
                    "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"};

            return monthNames[monthNum - 1] + " " + year;
        } catch (Exception e) {
            return month;
        }
    }

    private void stylePieChart(JFreeChart chart) {
        chart.setBackgroundPaint(BACKGROUND_COLOR);
        chart.setPadding(new RectangleInsets(30, 30, 30, 30));

        PiePlot plot = (PiePlot) chart.getPlot();

        plot.setBackgroundPaint(BACKGROUND_COLOR);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setLabelLinksVisible(false);
        plot.setLabelBackgroundPaint(null);
        plot.setLabelOutlinePaint(null);
        plot.setLabelShadowPaint(null);
        plot.setLabelPaint(TEXT_COLOR);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 18);
        plot.setLabelFont(labelFont);

        for (int i = 0; i < PIE_COLORS.length; i++) {
            plot.setSectionPaint(i, PIE_COLORS[i]);
        }

        chart.getLegend().setBackgroundPaint(BACKGROUND_COLOR);
        chart.getLegend().setItemPaint(TEXT_COLOR);

        Font legendFont = new Font("Segoe UI", Font.PLAIN, 17);
        chart.getLegend().setItemFont(legendFont);

        chart.getLegend().setBorder(0, 0, 0, 0);
        chart.getLegend().setPadding(10, 10, 10, 10);
    }

    private void styleLineChartWithForecast(JFreeChart chart) {
        chart.setBackgroundPaint(BACKGROUND_COLOR);
        chart.setPadding(new RectangleInsets(30, 30, 30, 30));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(BACKGROUND_COLOR);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(GRID_COLOR);
        plot.setRangeGridlineStroke(new BasicStroke(1.5f));
        plot.setDomainGridlinePaint(GRID_COLOR);
        plot.setDomainGridlineStroke(new BasicStroke(1.5f));

        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();

        renderer.setSeriesPaint(0, new Color(74, 144, 226));
        renderer.setSeriesPaint(1, new Color(230, 85, 80));

        renderer.setSeriesPaint(2, new Color(74, 144, 226));
        renderer.setSeriesPaint(3, new Color(230, 85, 80));

        renderer.setSeriesStroke(0, new BasicStroke(3.0f));
        renderer.setSeriesStroke(1, new BasicStroke(3.0f));

        renderer.setSeriesStroke(2, new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{6, 6}, 0));
        renderer.setSeriesStroke(3, new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{6, 6}, 0));

        renderer.setSeriesLinesVisible(2, true);
        renderer.setSeriesLinesVisible(3, true);

        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShapesVisible(2, false);
        renderer.setSeriesShapesVisible(3, false);

        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));
        renderer.setSeriesShape(1, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));

        plot.getDomainAxis().setAxisLinePaint(GRID_COLOR);
        plot.getDomainAxis().setTickMarkPaint(GRID_COLOR);
        plot.getDomainAxis().setLabelPaint(TEXT_COLOR);
        plot.getDomainAxis().setTickLabelPaint(TEXT_COLOR);
        plot.getDomainAxis().setTickLabelFont(LABEL_FONT);

        plot.getRangeAxis().setAxisLinePaint(GRID_COLOR);
        plot.getRangeAxis().setTickMarkPaint(GRID_COLOR);
        plot.getRangeAxis().setLabelPaint(TEXT_COLOR);
        plot.getRangeAxis().setTickLabelPaint(TEXT_COLOR);
        plot.getRangeAxis().setTickLabelFont(LABEL_FONT);

        plot.setAxisOffset(new RectangleInsets(10, 10, 10, 10));

        chart.getLegend().setBackgroundPaint(BACKGROUND_COLOR);
        chart.getLegend().setItemPaint(TEXT_COLOR);
        chart.getLegend().setItemFont(LEGEND_FONT);
    }

    private String convertChartToBase64(JFreeChart chart, int width, int height) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error converting chart to base64", e);
        }
    }

    private List<Double> forecastLinear(List<Double> data, int periods) {
        List<Double> forecast = new ArrayList<>();

        if (data.size() < 2) {
            double lastValue = data.isEmpty() ? 0 : data.get(data.size() - 1);
            for (int i = 0; i < periods; i++) {
                forecast.add(lastValue);
            }
            return forecast;
        }

        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        int n = data.size();

        for (int i = 0; i < n; i++) {
            sumX += i;
            sumY += data.get(i);
            sumXY += i * data.get(i);
            sumX2 += i * i;
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        for (int i = 0; i < periods; i++) {
            double prediction = intercept + slope * (n + i);
            forecast.add(Math.max(prediction, 0));
        }

        return forecast;
    }

}