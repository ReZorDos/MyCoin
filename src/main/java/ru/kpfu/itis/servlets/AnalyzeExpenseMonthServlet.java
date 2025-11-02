package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.service.analyze.AnalyzeService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebServlet("/analyze-expense/month")
public class AnalyzeExpenseMonthServlet extends HttpServlet {

    private AnalyzeService analyzeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        analyzeService = (AnalyzeService) config.getServletContext().getAttribute("analyzeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession(false).getAttribute("userId");
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = start.plusMonths(1).minusDays(1);

        List<ExpenseDto> expenseCategories = analyzeService.getMostExpenseCategoryByPeriod(userId, start, end);
        List<TransactionDto> lastTransactions = analyzeService.getLastFiveExpenseTransactions(userId);

        req.setAttribute("expenseCategories", expenseCategories);
        req.setAttribute("lastTransactions", lastTransactions);
        req.setAttribute("startDate", java.sql.Date.valueOf(start));
        req.setAttribute("endDate", java.sql.Date.valueOf(end));

        req.getRequestDispatcher("/jsp/analyze-expense-month.jsp").forward(req, resp);
    }
}