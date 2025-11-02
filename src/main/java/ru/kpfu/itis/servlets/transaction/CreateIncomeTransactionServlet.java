package ru.kpfu.itis.servlets.transaction;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.FieldErrorDto;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.dto.response.TransactionResponse;
import ru.kpfu.itis.service.income.IncomeService;
import ru.kpfu.itis.service.transaction.TransactionService;
import ru.kpfu.itis.service.user.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/create-transaction/income")
public class CreateIncomeTransactionServlet extends HttpServlet {

    private TransactionService transactionService;
    private IncomeService incomeService;
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        incomeService = (IncomeService) config.getServletContext().getAttribute("incomeService");
        transactionService = (TransactionService) config.getServletContext().getAttribute("transactionService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String categoryIdParam = req.getParameter("categoryId");
        req.setAttribute("preselectedCategoryId", UUID.fromString(categoryIdParam));

        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if (Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
        }
        req.getRequestDispatcher("/jsp/transaction/create-transaction-income.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID saveGoalId = (req.getParameter("saveGoalId") != null && !req.getParameter("saveGoalId").trim().isEmpty())
                ? UUID.fromString(req.getParameter("saveGoalId")) : null;

        TransactionDto request = TransactionDto.builder()
                .title(req.getParameter("title"))
                .sum(Double.parseDouble(req.getParameter("sum")))
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .incomeId(UUID.fromString(req.getParameter("incomeId")))
                .saveGoalId(saveGoalId)
                .build();

        TransactionResponse transactionResponse = transactionService.createIncomeTransaction(request);

        if (!transactionResponse.isSuccess()) {
            req.getSession(false).setAttribute("errors", transactionResponse.getErrors());
            resp.sendRedirect("/create-transaction/income");
        } else {
            userService.changeUserBalance(
                    (UUID) req.getSession(false).getAttribute("userId"),
                    "INCOME",
                    req.getParameter("sum")
            );
            incomeService.updateIncomeCategoryTotal(request.getIncomeId(), request.getSum());
            resp.sendRedirect("/profile");
        }
    }

}
