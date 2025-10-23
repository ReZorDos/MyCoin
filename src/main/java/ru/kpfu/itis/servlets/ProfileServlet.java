package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.repository.ExpenseCategoryRepository;
import ru.kpfu.itis.service.expense.ExpenseService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private ExpenseCategoryRepository expenseRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        expenseRepository = (ExpenseCategoryRepository) config.getServletContext().getAttribute("expenseRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID userId = (UUID) req.getSession(false).getAttribute("userId");
        List<ExpenseCategoryEntity> categories = expenseRepository.findAllCategoriesByIdUser(userId);

        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }
}