package ru.kpfu.itis.servlets.profile;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;
import ru.kpfu.itis.repository.IncomeCategoryRepository;
import ru.kpfu.itis.service.expense.ExpenseService;
import ru.kpfu.itis.service.income.IncomeService;
import ru.kpfu.itis.service.user.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private ExpenseService expenseService;
    private IncomeService incomeService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        expenseService = (ExpenseService) config.getServletContext().getAttribute("expenseService");
        incomeService = (IncomeService) config.getServletContext().getAttribute("incomeService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID userId = (UUID) req.getSession(false).getAttribute("userId");
        List<ExpenseCategoryEntity> expenseCategories = expenseService.getAllExpenseCategoriesByIdUser(userId);
        List<IncomeCategoryEntity> incomeCategories = incomeService.getAllIncomeCategoriesByIdUser(userId);
        double balance = userService.getUserBalance(userId);

        req.setAttribute("expenseCategories", expenseCategories);
        req.setAttribute("incomeCategories", incomeCategories);
        req.setAttribute("userBalance", balance);

        req.getRequestDispatcher("/jsp/profile/profile.jsp").forward(req, resp);
    }
}