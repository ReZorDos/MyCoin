package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;
import ru.kpfu.itis.repository.IncomeCategoryRepository;
import ru.kpfu.itis.service.expense.ExpenseService;
import ru.kpfu.itis.service.transaction.TransactionService;
import ru.kpfu.itis.service.user.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private ExpenseCategoryRepository expenseRepository;
    private IncomeCategoryRepository incomeRepository;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        expenseRepository = (ExpenseCategoryRepository) config.getServletContext().getAttribute("expenseRepository");
        incomeRepository = (IncomeCategoryRepository) config.getServletContext().getAttribute("incomeRepository");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID userId = (UUID) req.getSession(false).getAttribute("userId");
        List<ExpenseCategoryEntity> expenseCategories = expenseRepository.findAllCategoriesByIdUser(userId);
        List<IncomeCategoryEntity> incomeCategories = incomeRepository.findAllCategoriesByIdUser(userId);
        double balance = userService.getUserBalance(userId);

        req.setAttribute("expenseCategories", expenseCategories);
        req.setAttribute("incomeCategories", incomeCategories);
        req.setAttribute("userBalance", balance);

        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }
}