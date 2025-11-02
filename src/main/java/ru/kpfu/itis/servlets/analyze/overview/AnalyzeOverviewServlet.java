package ru.kpfu.itis.servlets.analyze.overview;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.categories.IncomeDto;
import ru.kpfu.itis.service.analyze.AnalyzeService;
import ru.kpfu.itis.service.user.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebServlet("/analyze/overview")
public class AnalyzeOverviewServlet extends HttpServlet {

    private AnalyzeService analyzeService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        analyzeService = (AnalyzeService) config.getServletContext().getAttribute("analyzeService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession(false).getAttribute("userId");

        LocalDate currentMonthStart = LocalDate.now().withDayOfMonth(1);
        LocalDate currentMonthEnd = currentMonthStart.plusMonths(1);
        LocalDate previousMonthStart = currentMonthStart.minusMonths(1);
        LocalDate previousMonthEnd = currentMonthStart;

        List<ExpenseDto> topExpenseCategories = analyzeService.getMostExpenseCategoryByPeriod(userId, currentMonthStart, currentMonthEnd);
        List<IncomeDto> topIncomeCategories = analyzeService.getMostIncomeCategoryByPeriod(userId, currentMonthStart, currentMonthEnd);
        List<TransactionDto> lastTransactions = analyzeService.getLastFiveTransactions(userId);

        Double currentMonthIncome = analyzeService.getTotalIncomesByPeriod(userId, currentMonthStart, currentMonthEnd);
        Double currentMonthExpense = analyzeService.getTotalExpensesByPeriod(userId, currentMonthStart, currentMonthEnd);
        Double previousMonthIncome = analyzeService.getTotalIncomesByPeriod(userId, previousMonthStart, previousMonthEnd);
        Double previousMonthExpense = analyzeService.getTotalExpensesByPeriod(userId, previousMonthStart, previousMonthEnd);

        Double incomePercentageChange = analyzeService.getPercentageChange(currentMonthIncome, previousMonthIncome);
        Double expensePercentageChange = analyzeService.getPercentageChange(currentMonthExpense, previousMonthExpense);

        Double currentBalance = userService.getUserBalance(userId);

        req.setAttribute("topExpenseCategories", topExpenseCategories);
        req.setAttribute("topIncomeCategories", topIncomeCategories);
        req.setAttribute("lastTransactions", lastTransactions);
        req.setAttribute("currentBalance", currentBalance);
        req.setAttribute("currentMonthIncome", currentMonthIncome);
        req.setAttribute("currentMonthExpense", currentMonthExpense);
        req.setAttribute("incomePercentageChange", incomePercentageChange);
        req.setAttribute("expensePercentageChange", expensePercentageChange);
        req.setAttribute("monthStart", java.sql.Date.valueOf(currentMonthStart));
        req.setAttribute("monthEnd", java.sql.Date.valueOf(currentMonthEnd.minusDays(1)));

        req.getRequestDispatcher("/jsp/analyze/analyze-overview.jsp").forward(req, resp);
    }
}