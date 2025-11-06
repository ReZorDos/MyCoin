package ru.kpfu.itis.servlets.category.expense;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.categories.ExpenseDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.model.ExpenseCategoryEntity;
import ru.kpfu.itis.service.expense.ExpenseService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
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
        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if (Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
        }

        String uuid = req.getParameter("uuid");

        ExpenseCategoryEntity category = expenseService.getCategoryById(UUID.fromString(uuid));
        req.setAttribute("category", category);

        String iconPath = getServletContext().getRealPath("/static/icons/expense");
        List<String> icons = expenseService.getAvailableIcons(iconPath);
        req.setAttribute("availableIcons", icons);

        req.getRequestDispatcher("/jsp/expense/edit-expense.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        ExpenseCategoryEntity expense = ExpenseCategoryEntity.builder()
                .name(req.getParameter("name"))
                .icon(req.getParameter("icon"))
                .build();

        ExpenseResponse expenseResponse = expenseService.updateExpenseCategory(uuid, expense, (UUID) req.getSession(false).getAttribute("userId"));

        if (!expenseResponse.isSuccess()) {
            req.getSession().setAttribute("errors", expenseResponse.getErrors());
            resp.sendRedirect("/expense-category/update?uuid=" + uuid);
        } else {
            resp.sendRedirect("/profile");
        }
    }
}
