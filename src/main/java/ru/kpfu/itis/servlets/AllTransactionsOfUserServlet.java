package ru.kpfu.itis.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.dto.TransactionDto;
import ru.kpfu.itis.model.TransactionEntity;
import ru.kpfu.itis.service.transaction.TransactionService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/all-transactions")
public class AllTransactionsOfUserServlet extends HttpServlet {

    private TransactionService transactionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        transactionService = (TransactionService) config.getServletContext().getAttribute("transactionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TransactionEntity> transactionList = transactionService.getAllTransactionsOfUser((UUID) req.getSession(false).getAttribute("userId"));
        req.setAttribute("transactionList", transactionList);
        req.getRequestDispatcher("/jsp/all-transactions-user.jsp").forward(req, resp);
    }
}
