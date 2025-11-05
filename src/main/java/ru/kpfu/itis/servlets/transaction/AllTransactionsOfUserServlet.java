package ru.kpfu.itis.servlets.transaction;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.model.TransactionEntity;
import ru.kpfu.itis.service.transaction.TransactionService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/all-transactions")
public class AllTransactionsOfUserServlet extends HttpServlet {

    private TransactionService transactionService;
    private static final int PAGE_SIZE = 20;
    private static final int DEFAULT_PAGE = 1;

    @Override
    public void init(ServletConfig config) throws ServletException {
        transactionService = (TransactionService) config.getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID userId = (UUID) req.getSession(false).getAttribute("userId");

        String paramValue = req.getParameter("page");
        int page = transactionService.getIntParameter(paramValue, "page", DEFAULT_PAGE);
        int size = PAGE_SIZE;

        List<TransactionEntity> transactionList = transactionService.getTransactionsWithPagination(userId, page, size);
        int totalTransactions = transactionService.getTotalTransactionsCount(userId);
        int totalPages = (int) Math.ceil((double) totalTransactions / size);

        int startPage = Math.max(1, page - 2);
        int endPage = Math.min(totalPages, page + 2);

        req.setAttribute("transactionList", transactionList);
        req.setAttribute("currentPage", page);
        req.setAttribute("pageSize", size);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalTransactions", totalTransactions);
        req.setAttribute("startPage", startPage);
        req.setAttribute("endPage", endPage);

        req.getRequestDispatcher("/jsp/transaction/all-transactions-user.jsp").forward(req, resp);
    }
}
