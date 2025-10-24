package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.ExpenseDto;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.response.ExpenseResponse;
import ru.kpfu.itis.service.expense.ExpenseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@WebServlet("/create-expense")
public class CreateExpenseCategoryServlet extends HttpServlet {

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

        String iconPath = getServletContext().getRealPath("/static/icons");
        List<String> icons = expenseService.getAvailableIcons(iconPath);
        req.setAttribute("availableIcons", icons);

        req.getRequestDispatcher("/jsp/create-expense.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ExpenseDto request = ExpenseDto.builder()
                .name(req.getParameter("name"))
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .icon(req.getParameter("icon"))
                .build();

        ExpenseResponse expenseResponse = expenseService.createExpenseCategory(request);

        if (!expenseResponse.isSuccess()) {
            req.getSession(false).setAttribute("errors", expenseResponse.getErrors());
            resp.sendRedirect("/create-expense");
        } else {
            resp.sendRedirect("/profile");
        }
    }
}
