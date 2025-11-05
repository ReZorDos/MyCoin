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
import ru.kpfu.itis.model.IncomeCategoryEntity;
import ru.kpfu.itis.model.SavingGoalDistribution;
import ru.kpfu.itis.model.SavingGoalEntity;
import ru.kpfu.itis.service.goals.SavingGoalService;
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
    private SavingGoalService savingGoalService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        incomeService = (IncomeService) config.getServletContext().getAttribute("incomeService");
        transactionService = (TransactionService) config.getServletContext().getAttribute("transactionService");
        userService = (UserService) config.getServletContext().getAttribute("userService");
        savingGoalService = (SavingGoalService) config.getServletContext().getAttribute("savingGoalService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession(false).getAttribute("userId");

        List<SavingGoalEntity> savingGoals = savingGoalService.getAllSavingGoalsByIdUser(userId);

        req.setAttribute("savingGoals",savingGoals);

        String categoryIdParam = req.getParameter("categoryId");
        req.setAttribute("preselectedCategoryId", UUID.fromString(categoryIdParam));
        IncomeCategoryEntity income = incomeService.getCategoryById(UUID.fromString(categoryIdParam));
        req.setAttribute("iconIncome", income.getIcon());
        req.setAttribute("nameIncome", income.getName());

        List<FieldErrorDto> errors = (List<FieldErrorDto>) req.getSession().getAttribute("errors");
        if (Objects.nonNull(errors)) {
            req.setAttribute("errors", errors);
        }
        req.getRequestDispatcher("/jsp/transaction/create-transaction-income.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<SavingGoalDistribution> distributions = savingGoalService.makeDistributionByGoals(
                req.getParameterValues("saveGoalIds"),
                req.getParameterValues("amounts")
        );

        TransactionDto request = TransactionDto.builder()
                .title(req.getParameter("title"))
                .sum(Double.parseDouble(req.getParameter("sum")))
                .userId((UUID) req.getSession(false).getAttribute("userId"))
                .incomeId(UUID.fromString(req.getParameter("incomeId")))
                .distributions(distributions)
                .build();

        TransactionResponse transactionResponse = transactionService.createIncomeTransaction(request);

        if (!transactionResponse.isSuccess()) {
            req.getSession(false).setAttribute("errors", transactionResponse.getErrors());
            String categoryId = req.getParameter("incomeId");
            resp.sendRedirect("/create-transaction/income?categoryId=" + categoryId);
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
