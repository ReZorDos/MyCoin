package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.service.expense.ExpenseService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/expense-category/update")
public class UpdateExpenseCategoryServlet extends HttpServlet {

    private ExpenseService expenseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        expenseService = (ExpenseService) config.getServletContext().getAttribute("expenseService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");

        ExpenseCategoryEntity category = expenseService.getCategoryById(UUID.fromString(uuid));
        req.setAttribute("category", category);

        String iconPath = getServletContext().getRealPath("/static/icons/expense");
        List<String> icons = expenseService.getAvailableIcons(iconPath);
        req.setAttribute("availableIcons", icons);

        req.getRequestDispatcher("/jsp/edit-expense.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        ExpenseCategoryEntity expense = ExpenseCategoryEntity.builder()
                .name(req.getParameter("name"))
                .icon(req.getParameter("icon"))
                .build();

        expenseService.updateExpenseCategory(uuid, expense);

        resp.sendRedirect("/profile");
    }
}
