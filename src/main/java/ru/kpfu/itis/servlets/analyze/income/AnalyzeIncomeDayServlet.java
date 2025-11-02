package ru.kpfu.itis.servlets.analyze.income;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.service.analyze.AnalyzeService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebServlet("/analyze-income/day")
public class AnalyzeIncomeDayServlet extends HttpServlet {

    private AnalyzeService analyzeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        analyzeService = (AnalyzeService) config.getServletContext().getAttribute("analyzeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession(false).getAttribute("userId");

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        LocalDate yesterday = today.minusDays(1);

        List<IncomeDto> incomeCategories = analyzeService.getMostIncomeCategoryByPeriod(userId, today, tomorrow);
        List<TransactionDto> lastTransactions = analyzeService.getLastFiveIncomeTransactions(userId);
        Double currentTotal = analyzeService.getTotalIncomesByPeriod(userId, today, tomorrow);
        Double previousTotal = analyzeService.getTotalIncomesByPeriod(userId, yesterday, today);
        Double percentageChange = analyzeService.getPercentageChange(currentTotal, previousTotal);

        req.setAttribute("incomeCategories", incomeCategories);
        req.setAttribute("lastTransactions", lastTransactions);
        req.setAttribute("startDate", java.sql.Date.valueOf(today));
        req.setAttribute("endDate", java.sql.Date.valueOf(today));
        req.setAttribute("currentTotal", currentTotal);
        req.setAttribute("previousTotal", previousTotal);
        req.setAttribute("percentageChange", percentageChange);


        req.getRequestDispatcher("/jsp/analyze/income/analyze-income-day.jsp").forward(req, resp);
    }
}
