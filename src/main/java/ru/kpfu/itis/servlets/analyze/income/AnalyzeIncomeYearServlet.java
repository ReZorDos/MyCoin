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

@WebServlet("/analyze-income/year")
public class AnalyzeIncomeYearServlet extends HttpServlet {

    private AnalyzeService analyzeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        analyzeService = (AnalyzeService) config.getServletContext().getAttribute("analyzeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession(false).getAttribute("userId");
        LocalDate start = LocalDate.now().withDayOfYear(1);
        LocalDate end = start.plusYears(1);

        LocalDate previousStart = start.minusYears(1);
        LocalDate previousEnd = previousStart.plusYears(1);

        List<IncomeDto> incomeCategories = analyzeService.getMostIncomeCategoryByPeriod(userId, start, end);
        List<TransactionDto> lastTransactions = analyzeService.getMostFiveIncomeTransactionsByPeriod(userId, start, end);
        Double currentTotal = analyzeService.getTotalIncomesByPeriod(userId, start, end);
        Double previousTotal = analyzeService.getTotalIncomesByPeriod(userId, previousStart, previousEnd);
        Double percentageChange = analyzeService.getPercentageChange(currentTotal, previousTotal);

        req.setAttribute("incomeCategories", incomeCategories);
        req.setAttribute("lastTransactions", lastTransactions);
        req.setAttribute("startDate", java.sql.Date.valueOf(start));
        req.setAttribute("endDate", java.sql.Date.valueOf(end.minusDays(1)));
        req.setAttribute("currentTotal", currentTotal);
        req.setAttribute("previousTotal", previousTotal);
        req.setAttribute("percentageChange", percentageChange);

        req.getRequestDispatcher("/jsp/analyze/income/analyze-income-year.jsp").forward(req, resp);
    }

}
