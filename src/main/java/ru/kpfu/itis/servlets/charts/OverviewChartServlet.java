package ru.kpfu.itis.servlets.charts;

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
import java.time.LocalDate;
import java.util.Base64;
import java.util.UUID;

@WebServlet("/chart/income-expense")
public class OverviewChartServlet extends HttpServlet {

    private ChartService chartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        chartService = (ChartService) config.getServletContext().getAttribute("chartService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate start = LocalDate.now().minusYears(1);
        LocalDate end = LocalDate.now().plusMonths(1);

        ChartDto chartDto = ChartDto.builder()
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .chartType("income-expense")
                .startDate(start)
                .endDate(end)
                .build();

        ChartData chartData = chartService.createIncomeExpenseLineChart(chartDto);
        resp.setContentType("image/png");
        byte[] imageBytes = Base64.getDecoder().decode(chartData.getImageBase64());
        try (OutputStream out = resp.getOutputStream()) {
            out.write(imageBytes);
            out.flush();
        }
    }
}