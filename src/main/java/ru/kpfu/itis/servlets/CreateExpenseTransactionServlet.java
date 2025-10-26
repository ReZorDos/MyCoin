package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.response.TransactionResponse;
import ru.kpfu.itis.service.expense.ExpenseService;
import ru.kpfu.itis.service.transaction.impl.TransactionServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/create-transaction/expense")
public class CreateExpenseTransactionServlet extends HttpServlet {

    private TransactionServiceImpl transactionService;
    private ExpenseService expenseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        transactionService = (TransactionServiceImpl) config.getServletContext().getAttribute("transactionService");
        expenseService = (ExpenseService) config.getServletContext().getAttribute("expenseService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String categoryIdParam = req.getParameter("categoryId");
        req.setAttribute("preselectedCategoryId", UUID.fromString(categoryIdParam));

        List<FieldErrorDto>  errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if (Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
        }
        req.getRequestDispatcher("/jsp/create-transaction-expense.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID saveGoalId = (req.getParameter("saveGoalId") != null && !req.getParameter("saveGoalId").trim().isEmpty())
                ? UUID.fromString(req.getParameter("saveGoalId")) : null;

        TransactionDto request = TransactionDto.builder()
                .title(req.getParameter("title"))
                .sum(Double.parseDouble(req.getParameter("sum")))
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .expenseId(UUID.fromString(req.getParameter("expenseId")))
                .saveGoalId(saveGoalId)
                .build();

        TransactionResponse transactionResponse = transactionService.createExpenseTransaction(request);

        if (!transactionResponse.isSuccess()) {
            req.getSession(false).setAttribute("errors", transactionResponse.getErrors());
            resp.sendRedirect("/create-transaction/expense");
        } else {
            expenseService.updateExpenseCategoryTotal(request.getExpenseId(), request.getSum());
            resp.sendRedirect("/profile");
        }
    }
}
