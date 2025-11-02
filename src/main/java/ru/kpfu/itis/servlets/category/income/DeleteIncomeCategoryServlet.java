package ru.kpfu.itis.servlets.category.income;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kpfu.itis.service.income.IncomeService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/income-category/delete")
public class DeleteIncomeCategoryServlet extends HttpServlet {

    private IncomeService incomeService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        incomeService = (IncomeService) config.getServletContext().getAttribute("incomeService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");

        boolean deleted = incomeService.deleteIncomeCategory(UUID.fromString(uuid));
        resp.sendRedirect("/profile");
    }
}
