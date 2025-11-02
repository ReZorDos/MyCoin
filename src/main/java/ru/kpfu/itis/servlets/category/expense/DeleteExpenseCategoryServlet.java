package ru.kpfu.itis.servlets.category.expense;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.service.expense.ExpenseService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/expense-category/delete")
public class DeleteExpenseCategoryServlet extends HttpServlet {

    private ExpenseService expenseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        expenseService = (ExpenseService) config.getServletContext().getAttribute("expenseService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");

        boolean deleted = expenseService.deleteExpenseCategory(UUID.fromString(uuid));
        resp.sendRedirect("/profile");
    }

}
