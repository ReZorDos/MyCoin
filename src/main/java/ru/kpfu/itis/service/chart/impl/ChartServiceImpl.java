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
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

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
    public ChartData getChartData(ChartDto request) {
        switch (request.getChartType()) {
            case "exepnse":
                return createExpensePieChart(request);
            case "":
                return createIncomePieChart(request);
            default:
                return createEmptyChart("no chart");
        }
    }

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
                .type("pie")
                .build();
    }

    @Override
    public ChartData createIncomePieChart(ChartDto request) {

        return new ChartData();
    }

    private ChartData createEmptyChart(String message) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue(message, 1);

        JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, true, false);
        stylePieChart(chart);

        String chartImage = convertChartToBase64(chart, 800, 500);
        return ChartData.builder()
                .userId(null)
                .imageBase64(chartImage)
                .type("pie")
                .period("empty")
                .build();
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
        plot.setLabelFont(LABEL_FONT);

        for (int i = 0; i < PIE_COLORS.length; i++) {
            plot.setSectionPaint(i, PIE_COLORS[i]);
        }

        chart.getLegend().setBackgroundPaint(BACKGROUND_COLOR);
        chart.getLegend().setItemPaint(TEXT_COLOR);
        chart.getLegend().setItemFont(LEGEND_FONT);
        chart.getLegend().setBorder(0, 0, 0, 0);
        chart.getLegend().setPadding(10, 10, 10, 10);
    }

    private void styleLineChart(JFreeChart chart) {
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
        renderer.setSeriesPaint(0, LINE_COLOR);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-4, -4, 8, 8));
        renderer.setSeriesFillPaint(0, new Color(74, 144, 226, 50));

        renderer.setDefaultToolTipGenerator(new org.jfree.chart.labels.StandardCategoryToolTipGenerator());

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

}