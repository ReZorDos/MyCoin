package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ChartDto;
import ru.kpfu.itis.model.ChartData;
import ru.kpfu.itis.service.chart.ChartService;

import java.io.IOException;
import java.io.OutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Base64;
import java.util.UUID;

@WebServlet("/chart/expense")
public class ExpenseChartServlet extends HttpServlet {

    private ChartService chartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        chartService = (ChartService) config.getServletContext().getAttribute("chartService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String period = req.getParameter("period");
        LocalDate start;
        LocalDate end;

        if ("week".equals(period)) {
            start = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            end = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1);
        } else if ("year".equals(period)) {
            start = LocalDate.now().withDayOfYear(1);
            end = start.plusYears(1);
        } else if ("day".equals(period)) {
            start = LocalDate.now();
            end = start.plusDays(1);
        } else {
            start = LocalDate.now().withDayOfMonth(1);
            end = start.plusMonths(1);
        }

        ChartDto chartDto = ChartDto.builder()
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .chartType("expense")
                .startDate(start)
                .endDate(end)
                .build();

        ChartData chartData = chartService.createExpensePieChart(chartDto);
        resp.setContentType("image/png");
        byte[] imageBytes = Base64.getDecoder().decode(chartData.getImageBase64());
        try (OutputStream out = resp.getOutputStream()) {
            out.write(imageBytes);
            out.flush();
        }
    }
}